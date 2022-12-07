package com.nailseong.invitation.invitation.exception;

import com.nailseong.invitation.exception.BadRequestException;

public class InvalidMaxUsesException extends BadRequestException {

    private final static String MESSAGE = "초대장의 최대 사용 횟수가 유효하지 않습니다.";

    public InvalidMaxUsesException() {
        super(MESSAGE);
    }
}
