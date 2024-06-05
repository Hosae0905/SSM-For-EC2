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
public class PostMemberSignupRes {

    @NotBlank
    private Long memberIdx;

    @NotBlank
    @Size(max = 45)
    private String memberEmail;

    @NotBlank
    @Size(max = 20)
    private String memberName;

    public static PostMemberSignupRes buildSignUpRes(Member member) {
        return PostMemberSignupRes.builder()
                .memberIdx(member.getIdx())
                .memberName(member.getMemberName())
                .memberEmail(member.getMemberEmail())
                .build();
    }
}
