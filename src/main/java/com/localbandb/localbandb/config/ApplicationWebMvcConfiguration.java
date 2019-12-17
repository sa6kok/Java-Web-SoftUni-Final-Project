package com.localbandb.localbandb.config;

import com.localbandb.localbandb.web.interceptors.PageTitleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationWebMvcConfiguration implements WebMvcConfigurer {

    private final PageTitleInterceptor pageTitleInterceptor;

    @Autowired
    public ApplicationWebMvcConfiguration(PageTitleInterceptor pageTitleInterceptor) {
        this.pageTitleInterceptor = pageTitleInterceptor;

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(pageTitleInterceptor);
    }
}