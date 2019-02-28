package com.tensquare.friend.config;

import com.tensquare.friend.interceptor.JwtInperceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    private JwtInperceptor jwtInperceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInperceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/**/login/**");
    }
}
