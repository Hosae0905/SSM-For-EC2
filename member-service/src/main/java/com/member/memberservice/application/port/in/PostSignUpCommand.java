package com.member.memberservice.application.port.in;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostSignUpCommand {


    public static PostSignUpCommand buildCommand() {
        return PostSignUpCommand.builder().build();
    }
}
