package com.project.ssm.notification.controller;


import com.project.ssm.notification.service.EmittersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;


@RestController
@Slf4j
@RequiredArgsConstructor
public class NotificationController {

    private final EmittersService emittersService;

    @RequestMapping(value = "/notification/{memberEmail}", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter handle(@PathVariable String memberEmail) {
        SseEmitter emitter = new SseEmitter(3600000L);
        log.info("Emitter for client {}: {}", memberEmail, emitter);

        emittersService.getEmitters().put(memberEmail, emitter);

        emitter.onCompletion(() -> {
            log.info("Emitter completed for client {}", memberEmail);
            emittersService.getEmitters().remove(memberEmail);
        });

        emitter.onTimeout(() -> {
            log.info("Emitter timed out for client {}", memberEmail);
            emittersService.getEmitters().remove(memberEmail);
        });

        try {
            emitter.send(SseEmitter.event()
                    .name("test")
                    .data("SseEmitter 생성"));
        } catch (IOException e) {
            log.info("처음 이미터 보낼때 발생");
            throw new RuntimeException(e);
        }

        return emitter;
    }
}
