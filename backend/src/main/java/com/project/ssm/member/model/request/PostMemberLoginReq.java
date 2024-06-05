package com.project.ssm.member.model.request;

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
public class PostMemberLoginReq {

    @NotBlank
    @Size(max = 45)
    private String memberEmail;

    @NotBlank
    @Size(max = 45)
    private String password;
}
