package com.member.memberservice.adapter.in.web.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSignUpReq {
    private String memberEmail;
    private String password;
    private String memberName;
    private String department;
    private String position;
}
