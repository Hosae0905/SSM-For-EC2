package com.member.memberservice.domain;

import com.member.memberservice.adapter.out.persistence.MemberEntity;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Member {
    private final Long memberIdx;
    private final String memberEmail;

    public static Member buildMember(MemberEntity member) {
        return Member.builder().memberIdx(member.getIdx()).memberEmail(member.getMemberEmail()).build();
    }
}
