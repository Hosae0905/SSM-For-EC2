package com.member.memberservice.application.port.in;

import lombok.Builder;

@Builder
public class PostSignUpCommand {


    public static PostSignUpCommand buildCommand() {
        return PostSignUpCommand.builder().build();
    }
}
