package com.nailseong.invitation.channel.exception;

import com.nailseong.invitation.exception.BadRequestException;

public class NoLeftPeopleException extends BadRequestException {

    public static final String MESSAGE = "채널이 가득 찼습니다.";

    public NoLeftPeopleException() {
        super(MESSAGE);
    }
}
