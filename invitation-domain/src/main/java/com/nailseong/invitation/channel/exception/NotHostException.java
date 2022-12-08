package com.nailseong.invitation.channel.exception;

import com.nailseong.invitation.exception.BadRequestException;

public class NotHostException extends BadRequestException {

    public static final String MESSAGE = "방장 권한이 필요합니다.";

    public NotHostException() {
        super(MESSAGE);
    }
}
