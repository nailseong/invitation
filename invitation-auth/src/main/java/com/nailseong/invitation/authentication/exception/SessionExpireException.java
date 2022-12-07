package com.nailseong.invitation.authentication.exception;

import com.nailseong.invitation.exception.BadRequestException;

public class SessionExpireException extends BadRequestException {

    private static final String MESSAGE = "세션이 만료되었습니다.";

    public SessionExpireException() {
        super(MESSAGE);
    }
}
