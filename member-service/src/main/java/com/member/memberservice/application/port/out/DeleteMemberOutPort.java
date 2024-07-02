package com.member.memberservice.application.port.out;

import com.member.memberservice.application.port.in.DeleteMemberCommand;
import com.member.memberservice.domain.Member;

public interface DeleteMemberOutPort {
    Member deleteMember(DeleteMemberCommand command);
}
