package com.nailseong.invitation.member.exception;

import com.nailseong.invitation.exception.NotFoundException;

public class MemberNotFoundException extends NotFoundException {

    private static final String MESSAGE = "사용자가 존재하지 않습니다.";

    public MemberNotFoundException() {
        super(MESSAGE);
    }
}
