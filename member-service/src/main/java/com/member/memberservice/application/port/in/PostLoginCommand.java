package com.member.memberservice.application.port.in;

import com.member.memberservice.adapter.in.web.data.PostLoginReq;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostLoginCommand {
    private String memberEmail;
    private String password;

    public static PostLoginCommand buildCommand(PostLoginReq postLoginReq) {
        return PostLoginCommand.builder()
                .memberEmail(postLoginReq.getMemberEmail())
                .password(postLoginReq.getPassword())
                .build();
    }
}
