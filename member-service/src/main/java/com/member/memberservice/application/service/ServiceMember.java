package com.member.memberservice.application.service;

import com.member.memberservice.application.port.in.*;
import com.member.memberservice.application.port.out.*;
import com.member.memberservice.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceMember implements SignUpMemberInPort, LoginMemberInPort, UpdateMemberInPort, DeleteMemberInPort {

    private final SignupMemberOutPort signupMemberOutPort;
    private final LoginMemberOutPort loginMemberOutPort;
    private final UpdateMemberOutPort updateMemberOutPort;
    private final DeleteMemberOutPort deleteMemberOutPort;
    private final LoginMemberJwtOutPort loginMemberJwtOutPort;

    @Override
    public Member signup(SignUpMemberCommand command) {
        Member member = signupMemberOutPort.signUp(command);
        if (member != null) {
            return member;
        } else {
            return null;
        }
    }

    @Override
    public String login(LoginMemberCommand command) {
        Member member = loginMemberOutPort.login(command);
        if (member != null) {
            String token = loginMemberJwtOutPort.generateAccessToken(member);
            return token;
        } else {
            return "login was failed";
        }
    }

    @Override
    public Member update(UpdateMemberCommand command, String token) {
        if (loginMemberJwtOutPort.validateToken(token)) {
            return updateMemberOutPort.updateMember(command);
        } else {
            return null;
        }
    }

    @Override
    public Member delete(DeleteMemberCommand command, String token) {
        if (loginMemberJwtOutPort.validateToken(token)) {
            return deleteMemberOutPort.deleteMember(command);
        } else {
            return null;
        }
    }
}
