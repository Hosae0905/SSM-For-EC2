package com.project.ssm.member.model;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileIdx;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "memberIdx")
    private Member member;

    @Column(nullable = false, length = 500)
    private String imageAddr;

    public static ProfileImage createProfileImage(String imageAddr, Member member) {
        return ProfileImage.builder()
                .imageAddr(imageAddr)
                .member(member)
                .build();
    }
}
