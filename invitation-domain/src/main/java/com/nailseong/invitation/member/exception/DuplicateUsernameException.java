package com.nailseong.invitation.member.exception;

import com.nailseong.invitation.exception.BadRequestException;

public class DuplicateUsernameException extends BadRequestException {

    private static final String MESSAGE = "이미 사용중인 사용자 이름입니다.";

    public DuplicateUsernameException() {
        super(MESSAGE);
    }
}
