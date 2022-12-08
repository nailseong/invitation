package com.nailseong.invitation.channel.exception;

import com.nailseong.invitation.exception.BadRequestException;

public class AlreadyJoinException extends BadRequestException {

    public static final String MESSAGE = "이미 참여한 채널입니다.";

    public AlreadyJoinException() {
        super(MESSAGE);
    }
}
