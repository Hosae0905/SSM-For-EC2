package com.project.ssm.member.model;

import com.project.ssm.chat.model.entity.Message;
import com.project.ssm.chat.model.entity.RoomParticipants;
import com.project.ssm.member.model.request.PostMemberSignupReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberIdx;

    @Column(nullable = false, length = 45, unique = true)
    private String memberId;

    @Column(nullable = false, length = 200)
    private String memberPw;

    @Column(nullable = false, length = 20)
    private String memberName;

    //TODO: 24.03.12 존재하는 부서만 받을 수 있게 처리 필요
    @Column(nullable = false, length = 45)
    private String department;

    //TODO: 24.03.12 존재하는 직급만 받을 수 있게 처리 필요
    @Column(nullable = false, length = 45)
    private String position;

    @Column(nullable = false)
    private String startedAt;

    @Column(nullable = false)
    private String updatedAt;


    @Column(nullable = false)
    private String authority;

    private Boolean status;

//    @OneToMany(mappedBy = "member")
//    private List<Event> events;

    @OneToMany(mappedBy = "member")
    private List<Message> messages;

    //    @OneToMany(mappedBy = "member")
//    private List<ProfileImage> profileImage;

    @OneToMany(mappedBy = "member")
    private List<RoomParticipants> roomParticipantsList;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profileIdx", referencedColumnName = "profileIdx")
    private ProfileImage profileImage;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton((GrantedAuthority) () -> authority);
    }

    @Override
    public String getPassword() {
        return memberPw;
    }

    @Override
    public String getUsername() {
        return memberId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static Member createMember(PostMemberSignupReq memberInfo, ProfileImage profileImage) {
        return Member.builder()
                .memberId(memberInfo.getMemberId())
                .memberPw(memberInfo.getPassword())
                .memberName(memberInfo.getMemberName())
                .department(memberInfo.getDepartment())
                .position(memberInfo.getPosition())
                .status(true)
                .startedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))
                .updatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))
                .authority("ROLE_USER")
                .profileImage(profileImage)
                .build();
    }
}
