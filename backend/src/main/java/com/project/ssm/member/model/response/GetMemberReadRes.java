package com.project.ssm.member.model.response;

import com.project.ssm.member.model.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetMemberReadRes {

    @NotBlank
    @Size(max = 45)
    private String memberEmail;

    @NotBlank
    @Size(max = 20)
    private String memberName;

    @NotBlank
    @Size(max = 45)
    private String department;

    @NotBlank
    @Size(max = 45)
    private String position;

    private String profileImage;

    public static GetMemberReadRes buildReadRes(Member member) {
        return GetMemberReadRes.builder()
                .memberEmail(member.getMemberEmail())
                .memberName(member.getMemberName())
                .department(member.getDepartment())
                .position(member.getPosition())
                .profileImage(member.getProfileImage().getImageAddr())
                .build();
    }
}
