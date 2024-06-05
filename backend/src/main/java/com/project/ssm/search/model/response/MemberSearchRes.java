package com.project.ssm.search.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSearchRes {
    private Long memberIdx;
    private String memberEmail;
    private String memberName;
    private String position;
    private String department;
}
