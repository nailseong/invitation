package com.nailseong.invitation.invitation.exception;

import com.nailseong.invitation.exception.BadRequestException;

public class InvalidCodeException extends BadRequestException {

    public static final String MESSAGE = "초대장의 코드가 유효하지 않습니다.";

    public InvalidCodeException() {
        super(MESSAGE);
    }
}
