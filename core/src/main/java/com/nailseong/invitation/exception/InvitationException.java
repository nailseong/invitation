package com.nailseong.invitation.exception;

import org.springframework.http.HttpStatus;

public abstract class InvitationException extends RuntimeException {

    private final String clientMessage;
    private final HttpStatus httpStatus;

    protected InvitationException(final String clientMessage, final HttpStatus httpStatus) {
        super(clientMessage);
        this.clientMessage = clientMessage;
        this.httpStatus = httpStatus;
    }

    public String getClientMessage() {
        return clientMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
