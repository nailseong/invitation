package com.nailseong.invitation.channel.exception;

import com.nailseong.invitation.exception.BadRequestException;

public class NotChannelMemberException extends BadRequestException {

    public static final String MESSAGE = "채널에 가입한 사용자가 아닙니다.";

    public NotChannelMemberException() {
        super(MESSAGE);
    }
}
