package com.member.memberservice.application.port.in;

import lombok.Builder;

@Builder
public class DeleteMemberCommand {

    public static DeleteMemberCommand buildCommand() {
        return DeleteMemberCommand.builder().build();
    }
}
