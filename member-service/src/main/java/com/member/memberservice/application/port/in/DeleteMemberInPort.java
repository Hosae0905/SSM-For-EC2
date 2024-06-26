package com.member.memberservice.application.port.in;

import com.member.memberservice.domain.Member;

public interface DeleteMemberInPort {
    Member delete(DeleteMemberCommand command);
}
