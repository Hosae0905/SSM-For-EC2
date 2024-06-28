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
        return memberOutPort.signUp(command);
    }

    @Override
    public String login(PostLoginCommand command) {
        if (memberOutPort.login(command)) {
            return "ok";
        } else {
            return "login was failed";
        }
    }

    @Override
    public Member update(PatchUpdateCommand command) {
        return memberOutPort.updateMember(command);
    }

    @Override
    public String delete(DeleteMemberCommand command) {
        if(memberOutPort.deleteMember(command)) {
            return "ok";
        } else {
            return null;
        }
    }
}
