package com.project.ssm.member.repository;

import com.project.ssm.chat.model.entity.RoomParticipants;
import com.project.ssm.member.model.ProfileImage;

import java.util.List;
import java.util.Optional;

public interface MemberCustomRepository {

    ProfileImage findByMemberIdx(Long memberIdx);

    List<RoomParticipants> findChatRoomBymemberEmail(String memberEmail);


    List<RoomParticipants> findMemberNameByChatRoomName(String chatRoomId);
}
