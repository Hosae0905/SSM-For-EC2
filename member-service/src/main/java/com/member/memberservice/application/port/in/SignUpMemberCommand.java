package com.member.memberservice.application.port.in;

import com.member.memberservice.adapter.in.web.data.PostSignUpReq;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
public class SignUpMemberCommand {
    private String memberEmail;
    private String password;
    private String memberName;
    private String department;
    private String position;
    private MultipartFile profileImage;

    public static SignUpMemberCommand buildCommand(PostSignUpReq postSignUpReq, MultipartFile profileImage) {
        return SignUpMemberCommand.builder()
                .memberEmail(postSignUpReq.getMemberEmail())
                .password(postSignUpReq.getPassword())
                .memberName(postSignUpReq.getMemberName())
                .department(postSignUpReq.getDepartment())
                .position(postSignUpReq.getPosition())
                .profileImage(profileImage)
                .build();
    }
}
