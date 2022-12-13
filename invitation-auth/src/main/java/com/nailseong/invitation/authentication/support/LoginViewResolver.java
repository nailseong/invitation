package com.nailseong.invitation.authentication.support;

import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginViewResolver implements HandlerMethodArgumentResolver {

    private final HttpSession session;

    public LoginViewResolver(final HttpSession session) {
        this.session = session;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginView.class);
    }

    @Override
    public LoginSession resolveArgument(final MethodParameter parameter,
                                        final ModelAndViewContainer mavContainer,
                                        final NativeWebRequest webRequest,
                                        final WebDataBinderFactory binderFactory) {
        return (LoginSession) session.getAttribute("loginSession");
    }
}
