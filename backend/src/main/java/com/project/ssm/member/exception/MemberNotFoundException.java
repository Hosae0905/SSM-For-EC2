package com.project.ssm.member.exception;


import com.project.ssm.common.error.ErrorCode;
import com.project.ssm.common.error.exception.EntityNotFoundException;

public class MemberNotFoundException extends EntityNotFoundException {
    public MemberNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public static MemberNotFoundException forMemberId(String memberId) {
        return new MemberNotFoundException(ErrorCode.MEMBER_NOT_EXISTS, String.format("Member Id [ %s ] is not exists.", memberId));
    }

    public static MemberNotFoundException forMemberIdx(Long memberIdx) {
        return new MemberNotFoundException(ErrorCode.MEMBER_NOT_EXISTS, String.format("MemberIdx [ %s ] is not exists.", memberIdx));
    }
}
