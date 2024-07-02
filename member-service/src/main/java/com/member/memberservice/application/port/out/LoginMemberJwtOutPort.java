package com.member.memberservice.application.port.out;

import com.member.memberservice.domain.Member;

public interface LoginMemberJwtOutPort {
    String generateAccessToken(Member member);

    Boolean validateToken(String token);

}
