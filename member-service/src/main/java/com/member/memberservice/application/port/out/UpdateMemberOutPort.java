package com.member.memberservice.application.port.out;

import com.member.memberservice.application.port.in.UpdateMemberCommand;
import com.member.memberservice.domain.Member;

public interface UpdateMemberOutPort {
    Member updateMember(UpdateMemberCommand command);
}
