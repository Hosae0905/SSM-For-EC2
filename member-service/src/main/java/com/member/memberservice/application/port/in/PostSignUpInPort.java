package com.member.memberservice.application.port.in;

import com.member.memberservice.domain.Member;

public interface PostSignUpInPort {
    Member signup(PostSignUpCommand command);
}
