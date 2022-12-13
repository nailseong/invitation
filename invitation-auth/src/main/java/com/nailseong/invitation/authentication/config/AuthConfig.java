package com.nailseong.invitation.authentication.config;

import com.nailseong.invitation.authentication.support.LoginSessionResolver;
import com.nailseong.invitation.authentication.support.LoginViewResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthConfig implements WebMvcConfigurer {

    private final LoginSessionResolver loginSessionResolver;
    private final LoginViewResolver loginViewResolver;

    public AuthConfig(final LoginSessionResolver loginSessionResolver,
                      final LoginViewResolver loginViewResolver) {
        this.loginSessionResolver = loginSessionResolver;
        this.loginViewResolver = loginViewResolver;
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginSessionResolver);
        resolvers.add(loginViewResolver);
    }
}
