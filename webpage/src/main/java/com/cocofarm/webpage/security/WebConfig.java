package com.cocofarm.webpage.security;

import java.util.ArrayList;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static ArrayList<String> loginCheckPaths;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (loginCheckPaths == null) {
            setLoginCheckPaths();
        }
        registry.addInterceptor(new AdminCheckInterceptor()).addPathPatterns("/admin/**");
        registry.addInterceptor(new LoginCheckInterceptor()).addPathPatterns(loginCheckPaths);
    }

    private void setLoginCheckPaths() {
        loginCheckPaths = new ArrayList<>();
        loginCheckPaths.add("/*/qnawrite");
        loginCheckPaths.add("/*/modify");
        loginCheckPaths.add("/*/delete");
        loginCheckPaths.add("/*/write");
    }
}
