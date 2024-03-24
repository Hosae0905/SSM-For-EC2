package com.project.ssm.member.service;

import com.project.ssm.common.BaseResponse;
import com.project.ssm.member.config.filter.JwtFilter;
import com.project.ssm.member.config.utils.JwtUtils;
import com.project.ssm.member.exception.MemberAccountException;
import com.project.ssm.member.exception.MemberDuplicateException;
import com.project.ssm.member.exception.MemberNotFoundException;
import com.project.ssm.member.model.Member;
import com.project.ssm.member.model.request.GetMemberCheckIdReq;
import com.project.ssm.member.model.request.PatchMemberUpdatePasswordReq;
import com.project.ssm.member.model.request.PostMemberLoginReq;
import com.project.ssm.member.model.request.PostMemberSignupReq;
import com.project.ssm.member.model.response.GetMemberReadRes;
import com.project.ssm.member.model.response.PostMemberLoginRes;
import com.project.ssm.member.model.response.PostMemberSignupRes;
import com.project.ssm.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Literal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    private final MemberRepository memberRepository;
    private final ProfileImageService profileImageService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public BaseResponse signup(PostMemberSignupReq req, MultipartFile profileImage) {
        Optional<Member> byMemberId = memberRepository.findByMemberId(req.getMemberId());

        if (byMemberId.isPresent()) {
            throw MemberDuplicateException.forMemberId(req.getMemberId());
        }

        Member member = memberRepository.save(
                Member.createMember(req.getMemberId(), passwordEncoder.encode(req.getPassword()),
                req.getMemberName(), req.getDepartment(), req.getPosition()));

        profileImageService.registerProfileImage(member, profileImage);

        return BaseResponse.builder()
                .isSuccess(true)
                .code("MEMBER_001")
                .message("회원이 등록되었습니다.")
                .result(PostMemberSignupRes.builder()
                        .memberIdx(member.getMemberIdx())
                        .memberName(member.getMemberName())
                        .memberId(member.getMemberId())
                        .build())
                .build();
    }

    public BaseResponse login(PostMemberLoginReq req) {
        Optional<Member> byMemberId = memberRepository.findByMemberId(req.getMemberId());

        if (byMemberId.isEmpty()) {
            throw MemberNotFoundException.forMemberId(req.getMemberId());
        }
        Member member = byMemberId.get();
        if (passwordEncoder.matches(req.getPassword(), member.getPassword()) && member.getStatus().equals(true)) {
            return BaseResponse.builder()
                    .isSuccess(true)
                    .code("MEMBER_011")
                    .message("로그인에 성공하였습니다.")
                    .result(PostMemberLoginRes.builder()
                            .token(JwtUtils.generateAccessToken(member, secretKey, expiredTimeMs))
                            .build())
                    .build();
        } else {
            throw MemberAccountException.forInvalidPassword(req.getPassword());
        }
    }

    public BaseResponse checkId(GetMemberCheckIdReq req) {
        Optional<Member> byMemberId = memberRepository.findByMemberId(req.getMemberId());
        if (byMemberId.isPresent()) {
            return BaseResponse.builder()
                    .isSuccess(true)
                    .code("MEMBER_025")
                    .message("중복된 ID가 존재합니다.")
                    .result("fail")
                    .build();
        } else {
            return BaseResponse.builder()
                    .isSuccess(true)
                    .code("MEMBER_024")
                    .message("아이디 검사를 완료하였습니다.")
                    .result("ok")
                    .build();
        }
    }

    @Transactional
    public BaseResponse updatePassword(Member m, PatchMemberUpdatePasswordReq req) {
        Optional<Member> byId = memberRepository.findById(m.getMemberIdx());

        if (byId.isPresent()) {
            Member member = byId.get();
            // 기존 비밀번호가 일치하지 않았을 때
            if (!passwordEncoder.matches(req.getPassword(), member.getPassword())) {
                return BaseResponse.builder()
                        .isSuccess(true)
                        .code("MEMBER_36")
                        .message("기존 비밀번호가 일치하지 않습니다.")
                        .result("fail")
                        .build();
            }
            // 기존 비밀번호를 제대로 입력했지만 새로운 비밀번호가 기존의 비밀번호와 같을 때
            else if (passwordEncoder.matches(req.getPassword(), member.getPassword())
                    && passwordEncoder.matches(req.getNewPassword(), member.getPassword())) {
                return BaseResponse.builder()
                        .isSuccess(true)
                        .code("MEMBER_37")
                        .message("새로운 비밀번호는 기존의 비밀번호와 달라야 합니다.")
                        .result("fail")
                        .build();
            } else {
                member.setMemberPw(passwordEncoder.encode(req.getNewPassword()));
                member.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
                memberRepository.save(member);
            }

        }
        return BaseResponse.builder()
                .isSuccess(true)
                .code("MEMBER_35")
                .message("비밀번호 변경이 완료되었습니다.")
                .result("ok")
                .build();
    }

    public BaseResponse delete(Member m) {
        Optional<Member> byId = memberRepository.findById(m.getMemberIdx());

        if (byId.isPresent()) {
            Member member = byId.get();

            member.setStatus(false);
            member.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
            memberRepository.save(member);

            return BaseResponse.builder()
                    .isSuccess(true)
                    .code("MEMBER_48")
                    .message("회원 삭제가 정상적으로 처리되었습니다.")
                    .result("ok")
                    .build();
        }
        return null;
    }

    // 멤버 조회를 위한 메소드
    public BaseResponse read(){
        List<Member> all = memberRepository.findAll();
        List<GetMemberReadRes> members = new ArrayList<>();

        for (Member member : all) {
            GetMemberReadRes result = GetMemberReadRes.builder()
                    .memberId(member.getMemberId())
                    .memberName(member.getMemberName())
                    .department(member.getDepartment())
                    .position(member.getPosition())
                    .build();

            members.add(result);
        }

        return BaseResponse.builder()
                .isSuccess(true)
                .code("temp")
                .message("회원조회가 성공했습니다")
                .result(members)
                .build();
    }
}
