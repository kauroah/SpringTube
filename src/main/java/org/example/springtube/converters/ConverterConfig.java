package org.example.springtube.converters;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConverterConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // Registering the custom converter StringToLocalDateTimeConverterService
        registry.addConverter(new StringToLocalDateTimeConverterService());
    }
}