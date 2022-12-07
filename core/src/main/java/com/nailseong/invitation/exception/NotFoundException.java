package com.nailseong.invitation.exception;

import org.springframework.http.HttpStatus;

public abstract class NotFoundException extends InvitationException {

    protected NotFoundException(final String clientMessage) {
        super(clientMessage, HttpStatus.NOT_FOUND);
    }
}
