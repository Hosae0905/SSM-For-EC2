package com.member.memberservice.application.port.out;

import com.member.memberservice.application.port.in.SignUpMemberCommand;
import com.member.memberservice.domain.Member;

public interface SignupMemberOutPort {
    Member signUp(SignUpMemberCommand command);
}
