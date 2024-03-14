package com.project.ssm.calendar.service;

import com.project.ssm.calendar.model.entity.PersonalEvent;
import com.project.ssm.calendar.model.request.GetPersonalEventReq;
import com.project.ssm.calendar.model.request.PostPersonalEventReq;
import com.project.ssm.calendar.model.response.GetPersonalEventRes;
import com.project.ssm.calendar.model.response.GetPersonalEventsRes;
import com.project.ssm.calendar.model.response.GetPersonalEventsListRes;
import com.project.ssm.calendar.repository.PersonalEventRepository;
import com.project.ssm.common.BaseResponse;
import com.project.ssm.member.model.Member;
import com.project.ssm.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.project.ssm.calendar.model.response.PostPersonalEventRes.postPersonalEventResBuilder;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonalEventService {

    private final PersonalEventRepository personalEventRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public BaseResponse create(PostPersonalEventReq request, String memberId) {

        // Optional<Member> Optionalmember = memberRepository.findByMemberId(memberId);
        Member member = memberRepository.findByMemberId(memberId).orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));

        // to Entity
        PersonalEvent personalEvent = request.postPersonalEventReqBuilder(member);

        personalEventRepository.save(personalEvent);

        return BaseResponse.BaseResponseBuilder("CALENDAR_001", true, "새로운 일정이 생성되었습니다.", postPersonalEventResBuilder(member.getMemberIdx(), personalEvent.getIdx(), personalEvent.getTitle()));

    }

    public BaseResponse findByYear(int year, String memberId) {

        Member member = memberRepository.findByMemberId(memberId).orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));

        List<PersonalEvent> memberEvents = member.getPersonalEvents();
        List<GetPersonalEventsListRes> yearEvents = new ArrayList<>();

        for (PersonalEvent event: memberEvents) {
            LocalDateTime startedAt = event.getStartedAt();
            LocalDateTime closedAt = event.getClosedAt();

            if(startedAt.getYear()==year||closedAt.getYear()==year){
                GetPersonalEventsListRes eventsListRes = GetPersonalEventsListRes.getPersonalEventsListResBuilder(event.getIdx(), event.getTitle(), event.getStartedAt(), event.getClosedAt());
                yearEvents.add(eventsListRes);
            }
        }

        return BaseResponse.BaseResponseBuilder("CALENDAR_010", true, "일정이 조회되었습니다.", GetPersonalEventsRes.getPersonalEventResBuilder(member.getMemberIdx(), yearEvents));

    }

    public BaseResponse findByEventIdx(GetPersonalEventReq request) {

        Member member = memberRepository.findByMemberId(request.getMemberId()).orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));

        PersonalEvent personalEvent = personalEventRepository.findById(request.getEventIdx()).orElseThrow(() -> new IllegalArgumentException("없는 일정입니다."));

        return BaseResponse.BaseResponseBuilder("CALENDAR_010", true, "일정이 조회되었습니다.", GetPersonalEventRes.getPersonalEventResBuilder(member.getMemberIdx(), personalEvent.getIdx(), personalEvent.getTitle(), personalEvent.getPriority(), personalEvent.getIsLooped(), personalEvent.getStartedAt(), personalEvent.getClosedAt()));

    }
}
