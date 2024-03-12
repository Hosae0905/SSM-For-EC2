package com.project.ssm.chat.controller;

import com.project.ssm.chat.model.request.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;

//    @MessageMapping("/message")
//    public void sendMessage(MessageDto message) {
//        log.info("username = {}", message.getUserName());
//        log.info("message = {}", message.getMessage());
//        messagingTemplate.convertAndSend("/room/message", message);
//    }

    @MessageMapping("/message")
    public void test(MessageDto messageDto) {
        log.info("username = {}", messageDto.getUserName());
        log.info("message = {}", messageDto.getMessage());
    }

//    @MessageMapping("/room/entered/{roomId}")
//    public void enterRoom(@DestinationVariable(value = "roomId") String roomId, MessageDto messageDto) {
//        log.info("roomId = {}", roomId);
//        log.info("message = {}", messageDto);
//
//        messagingTemplate.convertAndSend("/sub/room/" + roomId, messageDto.getMessage());
//    }

    @MessageMapping("/room/{roomId}")
    public void sendMessage(@DestinationVariable(value = "roomId") String roomId, MessageDto messageDto) {
        log.info("roomId = {}", roomId);
        log.info("message = {}", messageDto);

        messagingTemplate.convertAndSend("/sub/room/" + roomId, messageDto);
    }
}
