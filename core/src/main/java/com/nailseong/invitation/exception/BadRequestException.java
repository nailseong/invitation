package com.nailseong.invitation.exception;

import org.springframework.http.HttpStatus;

public abstract class BadRequestException extends InvitationException {

    public BadRequestException(final String clientMessage) {
        super(clientMessage, HttpStatus.BAD_REQUEST);
    }
}
