package com.nailseong.invitation.authentication.config;

import com.nailseong.invitation.authentication.LoginSessionResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthConfig implements WebMvcConfigurer {

    private final LoginSessionResolver loginSessionResolver;

    public AuthConfig(final LoginSessionResolver loginSessionResolver) {
        this.loginSessionResolver = loginSessionResolver;
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginSessionResolver);
    }
}
