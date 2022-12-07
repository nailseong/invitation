package com.nailseong.invitation.channel.exception;

import com.nailseong.invitation.exception.BadRequestException;

public class InvalidMaxPeopleException extends BadRequestException {

    private final static String MESSAGE = "최대 인원이 유효하지 않습니다.";

    public InvalidMaxPeopleException() {
        super(MESSAGE);
    }
}
