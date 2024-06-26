package com.member.memberservice.application.port.in;

import lombok.Builder;

@Builder
public class PatchUpdateCommand {

    public static PatchUpdateCommand buildCommand() {
        return PatchUpdateCommand.builder().build();
    }
}
