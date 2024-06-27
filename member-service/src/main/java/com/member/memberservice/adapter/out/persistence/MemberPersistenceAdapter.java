package com.member.memberservice.adapter.out.persistence;

import com.member.memberservice.application.port.in.DeleteMemberCommand;
import com.member.memberservice.application.port.in.PatchUpdateCommand;
import com.member.memberservice.application.port.in.PostLoginCommand;
import com.member.memberservice.application.port.in.PostSignUpCommand;
import com.member.memberservice.application.port.out.MemberOutPort;
import com.member.memberservice.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements MemberOutPort {

    private final MemberRepository memberRepository;

    @Override
    public Member signUp(PostSignUpCommand command) {
        return null;
    }

    @Override
    public Member login(PostLoginCommand command) {
        return null;
    }

    @Override
    public Member updateMember(PatchUpdateCommand command) {
        return null;
    }

    @Override
    public Member deleteMember(DeleteMemberCommand command) {
        return null;
    }
}
