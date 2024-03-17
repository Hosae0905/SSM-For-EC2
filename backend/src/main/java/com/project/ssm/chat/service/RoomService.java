package com.project.ssm.chat.service;

import com.project.ssm.chat.exception.ChatRoomNotFoundException;
import com.project.ssm.chat.exception.MessageAccessException;
import com.project.ssm.chat.exception.MessageNotFoundException;
import com.project.ssm.chat.model.entity.ChatRoom;
import com.project.ssm.chat.model.entity.Message;
import com.project.ssm.chat.model.entity.RoomParticipants;
import com.project.ssm.chat.model.request.PatchUpdateRoomReq;
import com.project.ssm.chat.model.request.PostCreateRoomReq;
import com.project.ssm.chat.model.response.*;
import com.project.ssm.chat.repository.ChatRoomRepository;
import com.project.ssm.chat.repository.MessageRepository;
import com.project.ssm.chat.repository.RoomParticipantsRepository;
import com.project.ssm.common.BaseResponse;
import com.project.ssm.member.config.utils.JwtUtils;
import com.project.ssm.member.exception.MemberNotFoundException;
import com.project.ssm.member.model.Member;
import com.project.ssm.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final RoomParticipantsRepository roomPartRepository;
    private final MessageRepository messageRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;

    public BaseResponse<List<GetRoomListRes>> getRoomList(String token) {

        token = JwtUtils.checkJwtToken(token);
        String memberId = JwtUtils.getUserMemberId(token, secretKey);
        List<RoomParticipants> roomParticipants = roomPartRepository.findAllByMember_MemberId(memberId);
        List<GetRoomListRes> roomListRes = new ArrayList<>();

        if (roomParticipants.isEmpty()) {
            roomListRes.add(GetRoomListRes.buildDto("", ""));
            return BaseResponse.successRes("CHATTING_002", true, "채팅방이 조회되었습니다.", roomListRes);
        } else {
            for (RoomParticipants roomParticipant : roomParticipants) {
                ChatRoom chatRoom = roomParticipant.getChatRoom();
                roomListRes.add(GetRoomListRes.buildDto(chatRoom.getChatRoomId(), chatRoom.getChatRoomName()));
            }
            return BaseResponse.successRes("CHATTING_002", true, "채팅방이 조회되었습니다.", roomListRes);
        }
    }

    @Transactional
    public BaseResponse<PostCreateRoomRes> createRoom(PostCreateRoomReq postCreateRoom) {
        ChatRoom room = ChatRoom.createRoom(postCreateRoom.getChatRoomName());
        chatRoomRepository.save(room);
        for (String memberId : postCreateRoom.getMemberId()) {
            Member member = memberRepository.findByMemberId(memberId).orElseThrow(() ->
                    MemberNotFoundException.forMemberId(memberId));
            roomPartRepository.save(RoomParticipants.buildRoomPart(member, room));
        }
        PostCreateRoomRes postCreateRoomRes = PostCreateRoomRes.buildRoomRes(room.getChatRoomName(), room.getChatRoomId());
        return BaseResponse.successRes("CHATTING_001", true, "채팅방이 생성되었습니다.", postCreateRoomRes);
    }

    public BaseResponse<GetRoomInfoRes> getRoomInfo(String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findByChatRoomId(roomId).orElseThrow(() ->
                ChatRoomNotFoundException.forNotFoundChatRoom());

        List<ReturnMessageRes> messageList = new ArrayList<>();
        List<Message> messages = chatRoom.getMessages();

        for (Message message : messages) {
            messageList.add(ReturnMessageRes.buildMessage(message.getMessage(), message.getMember().getMemberName(), message.getCreatedAt()));
        }
        return BaseResponse.successRes("CHATTING_003", true, "채팅방 조회를 성공하였습니다.",
                GetRoomInfoRes.buildDto(chatRoom.getChatRoomName(), messageList));
    }

    public BaseResponse<List<GetChatListRes>> getChatList(String token, String chatRoomId, Integer page, Integer size) {
        List<GetChatListRes> chatList = new ArrayList<>();

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Message> list = messageRepository.findList(pageable, chatRoomId);

        for (Message message : list) {
            chatList.add(GetChatListRes.buildChatList(message.getMessage(), message.getCreatedAt(), message.getMember().getMemberName()));
        }
        return BaseResponse.successRes("CHATTING_000", true, "메시지 조회를 성공하였습니다.", chatList);
    }

    public Object updateRoom(PatchUpdateRoomReq patchUpdateRoomReq) {
        List<String> memberIdList = new ArrayList<>();
        ChatRoom chatRoom = chatRoomRepository.findByChatRoomId(patchUpdateRoomReq.getChatRoomIdx()).orElseThrow(() ->
                ChatRoomNotFoundException.forNotFoundChatRoom());

        for (String memberId : patchUpdateRoomReq.getMemberId()) {
            Member member = memberRepository.findByMemberId(memberId).orElseThrow(() ->
                    MemberNotFoundException.forMemberId(memberId));
            roomPartRepository.save(RoomParticipants.buildRoomPart(member, chatRoom));
            memberIdList.add(memberId);
        }

        return BaseResponse.successRes("CHATTING_000", true, "",
                PatchUpdateRoomRes.buildRoom(patchUpdateRoomReq.getChatRoomIdx(), chatRoom.getChatRoomName(), memberIdList));

    }

    public BaseResponse<DeleteRoomRes> deleteChatRoom(String chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findByChatRoomId(chatRoomId).orElseThrow(() ->
                ChatRoomNotFoundException.forNotFoundChatRoom());
        chatRoom.setChatRoomStatus(false);
        chatRoom.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        chatRoomRepository.save(chatRoom);
        return BaseResponse.successRes("CHATTING_000", true, "",
                DeleteRoomRes.buildDeleteRoom(chatRoomId, chatRoom.getUpdatedAt()));

    }

    public String outRoom(String token, String chatRoomIdx) {
        token = JwtUtils.checkJwtToken(token);
        String memberId = JwtUtils.getUserMemberId(token, secretKey);

        Optional<Member> member = memberRepository.findByMemberId(memberId);
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByChatRoomId(chatRoomIdx);

        if (member.isPresent() && chatRoom.isPresent()) {
            for (RoomParticipants roomParticipants : member.get().getRoomParticipantsList()) {
                if (roomParticipants.getChatRoom().getChatRoomId().equals(chatRoomIdx)) {
                    roomPartRepository.deleteById(roomParticipants.getRoomParticipantsIdx());
                }
            }
            return "ok";
        }

        return null;
    }

    public String deleteMessage(String token, Long messageIdx) {
        token = JwtUtils.checkJwtToken(token);
        String memberId = JwtUtils.getUserMemberId(token, secretKey);

        Member member = memberRepository.findByMemberId(memberId).orElseThrow(() ->
                MemberNotFoundException.forMemberId(memberId));

        Message message = messageRepository.findById(messageIdx).orElseThrow(() ->
                MessageNotFoundException.forNotFoundMessage(messageIdx));

        if(message.getMember().getMemberId().equals(member.getMemberId())) {
            messageRepository.deleteById(messageIdx);
            return "";
        } else {
            throw MessageAccessException.forDeleteMessage();
        }
    }
}
