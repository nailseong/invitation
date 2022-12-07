package com.nailseong.invitation.channel.exception;

import com.nailseong.invitation.exception.NotFoundException;

public class ChannelNotFoundException extends NotFoundException {

    private static final String MESSAGE = "채널이 존재하지 않습니다.";

    public ChannelNotFoundException() {
        super(MESSAGE);
    }
}
