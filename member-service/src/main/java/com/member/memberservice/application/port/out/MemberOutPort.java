package com.member.memberservice.application.port.out;

import com.member.memberservice.application.port.in.DeleteMemberCommand;
import com.member.memberservice.application.port.in.PatchUpdateCommand;
import com.member.memberservice.application.port.in.PostLoginCommand;
import com.member.memberservice.application.port.in.PostSignUpCommand;
import com.member.memberservice.domain.Member;

public interface MemberOutPort {
    Member signUp(PostSignUpCommand command);
    Boolean login(PostLoginCommand command);
    Member updateMember(PatchUpdateCommand command);
    Boolean deleteMember(DeleteMemberCommand command);
}
