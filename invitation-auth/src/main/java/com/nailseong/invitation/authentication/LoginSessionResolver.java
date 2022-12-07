package com.nailseong.invitation.authentication;

import com.nailseong.invitation.authentication.annotation.Verified;
import com.nailseong.invitation.authentication.domain.LoginSession;
import com.nailseong.invitation.authentication.exception.SessionExpireException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginSessionResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Verified.class);
    }

    @Override
    public LoginSession resolveArgument(final MethodParameter parameter,
                                        final ModelAndViewContainer mavContainer,
                                        final NativeWebRequest webRequest,
                                        final WebDataBinderFactory binderFactory) {
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        final LoginSession loginSession = (LoginSession) request.getSession().getAttribute("loginSession");
        if (loginSession == null) {
            throw new SessionExpireException();
        }
        return loginSession;
    }
}
