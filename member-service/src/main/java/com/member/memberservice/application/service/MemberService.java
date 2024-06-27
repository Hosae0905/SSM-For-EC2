package com.member.memberservice.application.service;

import com.member.memberservice.application.port.in.*;
import com.member.memberservice.application.port.out.MemberOutPort;
import com.member.memberservice.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberInPort {

    private final MemberOutPort memberOutPort;

    @Override
    public Member signup(PostSignUpCommand command) {
        memberOutPort.signUp(command);
        return null;
    }

    @Override
    public Member login(PostLoginCommand command) {
        memberOutPort.login(command);
        return null;
    }

    @Override
    public Member update(PatchUpdateCommand command) {
        memberOutPort.updateMember(command);
        return null;
    }

    @Override
    public Member delete(DeleteMemberCommand command) {
        memberOutPort.deleteMember(command);
        return null;
    }
}
