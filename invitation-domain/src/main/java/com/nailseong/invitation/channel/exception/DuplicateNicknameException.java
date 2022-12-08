package com.nailseong.invitation.channel.exception;

import com.nailseong.invitation.exception.BadRequestException;

public class DuplicateNicknameException extends BadRequestException {

    public static final String MESSAGE = "닉네임이 중복됩니다.";

    public DuplicateNicknameException() {
        super(MESSAGE);
    }
}
