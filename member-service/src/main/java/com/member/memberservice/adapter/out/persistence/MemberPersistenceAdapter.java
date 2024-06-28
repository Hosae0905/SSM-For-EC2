package com.member.memberservice.adapter.out.persistence;

import com.member.memberservice.application.port.in.DeleteMemberCommand;
import com.member.memberservice.application.port.in.PatchUpdateCommand;
import com.member.memberservice.application.port.in.PostLoginCommand;
import com.member.memberservice.application.port.in.PostSignUpCommand;
import com.member.memberservice.application.port.out.MemberOutPort;
import com.member.memberservice.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements MemberOutPort {

    private final MemberRepository memberRepository;

    @Override
    public Member signUp(PostSignUpCommand command) {
        MemberEntity member = memberRepository.save(MemberEntity.buildMemberEntity());
        return Member.buildMember(member);
    }

    @Override
    public Boolean login(PostLoginCommand command) {
        Optional<MemberEntity> member = memberRepository.findByMemberEmail(command.getMemberEmail());

        if (member.isPresent()) {
            if (member.get().getMemberPw().equals(command.getPassword())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public Member updateMember(PatchUpdateCommand command) {
        Optional<MemberEntity> member = memberRepository.findByMemberEmail(command.getMemberEmail());
        if (member.isPresent()) {
            if (member.get().getMemberPw().equals(command.getPassword())) {
                member.get().setMemberPw(command.getNewPassword());
            }
        }
        return null;
    }

    @Override
    public Boolean deleteMember(DeleteMemberCommand command) {
        return memberRepository.deleteByMemberEmail(command.getMemberEmail());
    }
}
