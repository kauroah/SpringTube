package org.example.springtube.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**","/css/**", "/js/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/static/css/", "classpath:/static/js/");
    }

}

/**
 * The purpose of the WebMvcConfig class is to configure Spring to serve static resources
 * here ::::
 * The class configures Spring to serve static resources from multiple locations
 * within the classpath. This setup ensures that resources are accessible via specific
 * URL patterns, improving the organization and accessibility of static content in the application.
 */