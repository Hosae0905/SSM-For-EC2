package com.member.memberservice.application.port.in;

import com.member.memberservice.domain.Member;

public interface PostLoginInPort {
    Member login(PostLoginCommand command);
}
