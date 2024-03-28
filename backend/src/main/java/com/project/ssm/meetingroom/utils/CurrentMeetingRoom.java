package com.project.ssm.meetingroom.utils;

import com.project.ssm.events.model.entity.Event;
import com.project.ssm.events.repository.EventRepository;
import com.project.ssm.meetingroom.model.entity.MeetingRoom;
import com.project.ssm.meetingroom.repository.MeetingRoomRepository;
import com.project.ssm.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class CurrentMeetingRoom {

    private final EventRepository eventRepository;
    private final MeetingRoomRepository meetingRoomRepository;

    // 미팅룸 갖고 와
    // 미팅룸에 저장된 이벤트
    // 전체 미팅룸 보려면 저장된 미팅룸 전부 반복문 돌려
    // return은 미팅룸으로 해
    public MeetingRoom meetingRoomNow(Long meetingRoomIdx) {
        LocalDateTime now = LocalDateTime.now().withNano(0).withSecond(0);
        List<Event> events = eventRepository.findAllByMeetingRoomIdx(meetingRoomIdx);
        Optional<MeetingRoom> result = meetingRoomRepository.findById(meetingRoomIdx);
        MeetingRoom meetingRoom;
        if (result.isPresent()) {
            meetingRoom = result.get();
            if (events.isEmpty()) {
                meetingRoom.setIsAvailable(true);
                meetingRoomRepository.save(meetingRoom);
            } else {
                for (Event event : events) {
                    // 예약내역 이벤트의 시작시간
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime bookedEventStartTime = LocalDateTime.parse(event.getStartedAt(), formatter);

                    // 예약내역 이벤트의 끝시간
                    LocalDateTime bookedEventCloseTime = LocalDateTime.parse(event.getClosedAt(), formatter);
                    // 지금 내 시간이 예약내역 이벤트의 시작시간과 예약내역 이벤트의 끝시간에 있으면 meetingRoom.isAvailable의 값을 false로 set
                    if (now.isAfter(bookedEventStartTime) && now.isBefore(bookedEventCloseTime)) {
                        meetingRoom.setIsAvailable(false);
                        meetingRoomRepository.save(meetingRoom);
                    } else {
                        meetingRoom.setIsAvailable(true);
                        meetingRoomRepository.save(meetingRoom);
                    }
                }
            }

        } else {
            throw MemberNotFoundException.forMemberIdx(meetingRoomIdx);
        }
        return meetingRoom;
    }
}
