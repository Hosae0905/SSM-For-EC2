package com.member.memberservice.adapter.out.persistence;

import com.member.memberservice.application.port.in.DeleteMemberCommand;
import com.member.memberservice.application.port.in.UpdateMemberCommand;
import com.member.memberservice.application.port.in.LoginMemberCommand;
import com.member.memberservice.application.port.in.SignUpMemberCommand;
import com.member.memberservice.application.port.out.DeleteMemberOutPort;
import com.member.memberservice.application.port.out.LoginMemberOutPort;
import com.member.memberservice.application.port.out.SignupMemberOutPort;
import com.member.memberservice.application.port.out.UpdateMemberOutPort;
import com.member.memberservice.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PersistenceAdapterMember implements SignupMemberOutPort, LoginMemberOutPort, UpdateMemberOutPort, DeleteMemberOutPort {

    private final MemberRepository memberRepository;

    @Override
    public Member signUp(SignUpMemberCommand command) {
        MemberEntity member = memberRepository.save(MemberEntity.buildMemberEntity(command));
        return Member.buildMember(member);
    }

    @Override
    public Member login(LoginMemberCommand command) {
        Optional<MemberEntity> member = memberRepository.findByMemberEmail(command.getMemberEmail());

        if (member.isPresent()) {
            if (member.get().getMemberPw().equals(command.getPassword()) && member.get().getStatus()) {
                return Member.buildMember(member.get());
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public Member updateMember(UpdateMemberCommand command) {
        Optional<MemberEntity> member = memberRepository.findByMemberEmail(command.getMemberEmail());
        if (member.isPresent()) {
            if (member.get().getMemberPw().equals(command.getPassword())) {
                member.get().setMemberPw(command.getNewPassword());
            }
        }
        return null;
    }

    @Override
    public Member deleteMember(DeleteMemberCommand command) {
        Optional<MemberEntity> member = memberRepository.findByMemberEmail(command.getMemberEmail());
        if (member.isPresent()) {
            member.get().setStatus(false);
            MemberEntity deletedMember = memberRepository.save(member.get());
            return Member.buildMember(deletedMember);
        } else {
            return null;
        }
    }
}
