package com.member.memberservice.application.port.in;

import lombok.Builder;

@Builder
public class PostLoginCommand {

    public static PostLoginCommand buildCommand() {
        return PostLoginCommand.builder().build();
    }
}
