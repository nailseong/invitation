package com.nailseong.invitation.invitation.exception;

import com.nailseong.invitation.exception.BadRequestException;

public class NoLeftUsesException extends BadRequestException {

    public static final String MESSAGE = "초대장에 사용 가능 횟수가 남아있지 않습니다.";

    public NoLeftUsesException() {
        super(MESSAGE);
    }
}
