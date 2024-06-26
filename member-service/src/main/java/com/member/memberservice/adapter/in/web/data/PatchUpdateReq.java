package com.member.memberservice.adapter.in.web.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatchUpdateReq {
    private String password;
    private String newPassword;
}
