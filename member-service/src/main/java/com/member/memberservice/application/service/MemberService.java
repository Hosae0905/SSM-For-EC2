package com.member.memberservice.application.service;

import com.member.memberservice.application.port.in.*;
import com.member.memberservice.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements PostSignUpInPort, PostLoginInPort, PatchUpdateInPort, DeleteMemberInPort {

    @Override
    public Member delete(DeleteMemberCommand command) {
        return null;
    }

    @Override
    public Member update(PatchUpdateCommand command) {
        return null;
    }

    @Override
    public Member login(PostLoginCommand command) {
        return null;
    }

    @Override
    public Member signup(PostSignUpCommand command) {
        return null;
    }
}
