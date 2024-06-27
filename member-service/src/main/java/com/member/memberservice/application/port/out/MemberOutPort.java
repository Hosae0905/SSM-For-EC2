package com.member.memberservice.application.port.out;

import com.member.memberservice.application.port.in.DeleteMemberCommand;
import com.member.memberservice.application.port.in.PatchUpdateCommand;
import com.member.memberservice.application.port.in.PostLoginCommand;
import com.member.memberservice.application.port.in.PostSignUpCommand;
import com.member.memberservice.domain.Member;

public interface MemberOutPort {
    Member signUp(PostSignUpCommand command);
    Member login(PostLoginCommand command);
    Member updateMember(PatchUpdateCommand command);
    Member deleteMember(DeleteMemberCommand command);
}
