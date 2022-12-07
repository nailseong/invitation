package com.nailseong.invitation.authentication.presentation;

import com.nailseong.invitation.authentication.application.AuthService;
import com.nailseong.invitation.authentication.support.LoginSession;
import com.nailseong.invitation.authentication.presentation.dto.LoginRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final HttpSession session;

    public AuthController(final AuthService authService, final HttpSession session) {
        this.authService = authService;
        this.session = session;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/login")
    public void login(@RequestBody @Valid final LoginRequest request) {
        final LoginSession loginSession = authService.login(request.username());
        session.setAttribute("loginSession", loginSession);
    }
}
