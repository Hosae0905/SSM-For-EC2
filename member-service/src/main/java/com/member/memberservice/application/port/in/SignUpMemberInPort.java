package com.member.memberservice.application.port.in;

import com.member.memberservice.domain.Member;

public interface SignUpMemberInPort {
    Member signup(SignUpMemberCommand command);
}
