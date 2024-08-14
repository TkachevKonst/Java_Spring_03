package ru.gb;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ExceptionTypeProrerties.class)
public class ExceptionTypeAutoConfiguration {

    public ExceptionTypeAspect exceptionTypeAspect(ExceptionTypeProrerties properties) {
        return new ExceptionTypeAspect(properties);
    }
}
