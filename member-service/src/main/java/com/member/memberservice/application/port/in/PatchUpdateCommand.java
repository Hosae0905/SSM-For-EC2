package com.member.memberservice.application.port.in;

import com.member.memberservice.adapter.in.web.data.PatchUpdateReq;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PatchUpdateCommand {
    private String memberEmail;
    private String password;
    private String newPassword;

    public static PatchUpdateCommand buildCommand(PatchUpdateReq patchUpdateReq) {
        return PatchUpdateCommand.builder()
                .memberEmail(patchUpdateReq.getMemberEmail())
                .password(patchUpdateReq.getPassword())
                .newPassword(patchUpdateReq.getNewPassword())
                .build();
    }
}
