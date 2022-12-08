package com.nailseong.invitation.invitation.exception;

import com.nailseong.invitation.exception.BadRequestException;

public class InvitationExpireException extends BadRequestException {

    private static final String MESSAGE = "초대장이 만료되었습니다.";

    public InvitationExpireException() {
        super(MESSAGE);
    }
}
