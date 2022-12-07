package com.nailseong.invitation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(InvitationException.class)
    public ResponseEntity<ExceptionResponse> handleInvitationException(final InvitationException e) {
        final ExceptionResponse response = new ExceptionResponse(e.getClientMessage());
        return ResponseEntity.status(e.getHttpStatus())
                .body(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse handleValidationException(final Exception e) {
        return new ExceptionResponse("입력이 유효하지 않습니다.");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ExceptionResponse handleException(final Exception e) {
        return new ExceptionResponse(e.getMessage());
    }
}
