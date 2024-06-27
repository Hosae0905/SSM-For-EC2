package com.member.memberservice.application.port.in;

import com.member.memberservice.domain.Member;

public interface MemberInPort {
    Member signup(PostSignUpCommand command);
    Member login(PostLoginCommand command);
    Member update(PatchUpdateCommand command);
    Member delete(DeleteMemberCommand command);
}
