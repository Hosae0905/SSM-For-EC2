package com.project.ssm.member.repository;


import com.project.ssm.chat.model.entity.QChatRoom;
import com.project.ssm.chat.model.entity.QRoomParticipants;
import com.project.ssm.chat.model.entity.RoomParticipants;
import com.project.ssm.member.model.ProfileImage;
import com.project.ssm.member.model.QMember;
import com.project.ssm.member.model.QProfileImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public ProfileImage findByMemberIdx(Long memberIdx) {
        QMember member = QMember.member;
        QProfileImage profileImage = QProfileImage.profileImage;

        return queryFactory
                .select(profileImage)
                .from(profileImage)
                .leftJoin(member)
                .on(profileImage.member.memberIdx.eq(member.memberIdx))
                .where(profileImage.member.memberIdx.eq(memberIdx)).fetchOne();
    }

    @Override
    public List<RoomParticipants> findChatRoomBymemberEmail(String memberEmail) {
        QRoomParticipants roomParticipants = QRoomParticipants.roomParticipants;
        QChatRoom chatRoom = QChatRoom.chatRoom;

        return queryFactory
                .select(roomParticipants)
                .from(roomParticipants)
                .leftJoin(chatRoom)
                .on(roomParticipants.chatRoom.chatRoomIdx.eq(chatRoom.chatRoomIdx))
                .where(
                        roomParticipants.member.memberEmail.eq(memberEmail)
                )
                .fetch();
    }

    @Override
    public List<RoomParticipants> findMemberNameByChatRoomName(String chatRoomId) {
        QRoomParticipants roomParticipants = QRoomParticipants.roomParticipants;
        QChatRoom chatRoom = QChatRoom.chatRoom;
        QMember member = QMember.member;


        return queryFactory
                .select(roomParticipants)
                .from(roomParticipants)
                .leftJoin(chatRoom)
                .on(roomParticipants.chatRoom.chatRoomIdx.eq(chatRoom.chatRoomIdx))
                .leftJoin(member)
                .on(roomParticipants.member.memberIdx.eq(member.memberIdx))
                .where(
                    roomParticipants.chatRoom.chatRoomId.eq(chatRoomId)
                )
                .fetch();
    }
}
