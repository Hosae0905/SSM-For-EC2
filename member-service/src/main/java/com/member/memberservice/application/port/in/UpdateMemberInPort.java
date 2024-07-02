package com.member.memberservice.application.port.in;

import com.member.memberservice.domain.Member;

public interface UpdateMemberInPort {
    Member update(UpdateMemberCommand command, String token);
}
