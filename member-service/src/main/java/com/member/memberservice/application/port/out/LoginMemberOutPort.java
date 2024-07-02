package com.member.memberservice.application.port.out;

import com.member.memberservice.application.port.in.LoginMemberCommand;
import com.member.memberservice.domain.Member;

public interface LoginMemberOutPort {
    Member login(LoginMemberCommand command);
}
