package com.member.memberservice.application.port.in;

import com.member.memberservice.adapter.in.web.data.DeleteMemberReq;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DeleteMemberCommand {

    private String memberEmail;
    private String password;

    public static DeleteMemberCommand buildCommand(DeleteMemberReq deleteMemberReq) {
        return DeleteMemberCommand.builder()
                .memberEmail(deleteMemberReq.getMemberEmail())
                .password(deleteMemberReq.getPassword())
                .build();
    }
}
