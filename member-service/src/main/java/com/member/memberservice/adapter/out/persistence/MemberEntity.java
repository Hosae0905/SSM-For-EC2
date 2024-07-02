package com.member.memberservice.adapter.out.persistence;

import com.member.memberservice.application.port.in.SignUpMemberCommand;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity(name = "member")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    public static MemberEntity buildMemberEntity(SignUpMemberCommand command) {
        return MemberEntity.builder()
                .memberEmail(command.getMemberEmail())
                .memberPw(command.getPassword())
                .memberName(command.getMemberName())
                .department(command.getDepartment())
                .position(command.getPosition())
                .startedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))
                .updatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))
                .authority("ROLE_USER")
                .status(true)
                .build();
    }
}
