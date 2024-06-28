package com.member.memberservice.application.port.in;

import com.member.memberservice.adapter.in.web.data.PostSignUpReq;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
public class PostSignUpCommand {
    private String memberEmail;
    private String password;
    private String memberName;
    private String department;
    private String position;
    private MultipartFile profileImage;

    public static PostSignUpCommand buildCommand(PostSignUpReq postSignUpReq, MultipartFile profileImage) {
        return PostSignUpCommand.builder()
                .memberEmail(postSignUpReq.getMemberEmail())
                .password(postSignUpReq.getPassword())
                .memberName(postSignUpReq.getMemberName())
                .department(postSignUpReq.getDepartment())
                .position(postSignUpReq.getPosition())
                .profileImage(profileImage)
                .build();
    }
}
