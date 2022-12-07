package com.nailseong.invitation.authentication;

import com.nailseong.invitation.authentication.annotation.Verified;
import com.nailseong.invitation.authentication.domain.LoginSession;
import com.nailseong.invitation.authentication.domain.LoginSessionRepository;
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

    private final LoginSessionRepository loginSessionRepo;

    public LoginSessionResolver(final LoginSessionRepository loginSessionRepo) {
        this.loginSessionRepo = loginSessionRepo;
    }

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
        final String id = request.getSession().getId();
        return loginSessionRepo.findById(id)
                .orElseThrow(SessionExpireException::new);
    }
}
