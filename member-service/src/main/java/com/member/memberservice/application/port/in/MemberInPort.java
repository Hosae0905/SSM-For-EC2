package com.member.memberservice.application.port.in;

import com.member.memberservice.domain.Member;

public interface MemberInPort {
    Member signup(PostSignUpCommand command);
    String login(PostLoginCommand command);
    Member update(PatchUpdateCommand command);
    String delete(DeleteMemberCommand command);
}
