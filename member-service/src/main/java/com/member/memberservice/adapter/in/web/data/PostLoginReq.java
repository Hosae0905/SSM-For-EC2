package com.member.memberservice.adapter.in.web.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostLoginReq {
    private String memberEmail;
    private String password;
}
