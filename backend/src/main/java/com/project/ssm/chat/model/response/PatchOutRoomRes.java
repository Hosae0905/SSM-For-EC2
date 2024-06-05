package com.project.ssm.chat.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatchOutRoomRes {
    private String chatRoomId;
    private String memberEmail;

    public static PatchOutRoomRes buildOutRoom(String chatRoomId, String memberEmail) {
        return PatchOutRoomRes.builder()
                .chatRoomId(chatRoomId)
                .memberEmail(memberEmail)
                .build();
    }
}
