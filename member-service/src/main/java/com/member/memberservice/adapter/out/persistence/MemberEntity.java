package com.member.memberservice.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "member")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false, length = 45, unique = true)
    private String memberEmail;

    @Column(nullable = false, length = 200)
    private String memberPw;

    @Column(nullable = false, length = 20)
    private String memberName;

    @Column(nullable = false, length = 45)
    private String department;

    @Column(nullable = false, length = 45)
    private String position;

    @Column(nullable = false)
    private String startedAt;

    @Column(nullable = false)
    private String updatedAt;

    @Column(nullable = false)
    private String authority;

    private Boolean status;

    public static MemberEntity buildMemberEntity() {
        return MemberEntity.builder().build();
    }
}
