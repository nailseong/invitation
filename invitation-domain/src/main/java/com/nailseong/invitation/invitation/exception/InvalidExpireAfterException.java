package com.nailseong.invitation.invitation.exception;

import com.nailseong.invitation.exception.BadRequestException;

public class InvalidExpireAfterException extends BadRequestException {

    public static final String MESSAGE = "만료 기간이 유효하지 않습니다.";

    public InvalidExpireAfterException() {
        super(MESSAGE);
    }
}
