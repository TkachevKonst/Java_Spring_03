package ru.gb.spring.aspect;


import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class ExceptionTypeAspect {


    @Pointcut("execution(* ru.gb.spring.service.TimesheetService.*(..))")
    public void typeExceptionTypePointcut() {
    }

    @AfterThrowing(value = "typeExceptionTypePointcut()", throwing = "ex")
    public Object afterThrowingTypeException(JoinPoint jp, Exception e) {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Object target = jp.getTarget();
        Method method = methodSignature.getMethod();
        Class aClass = method.getReturnType();

        Object value = null;

        if (aClass.isPrimitive()) {
            value = aClass.getEnclosingMethod().getDefaultValue();
        }

        log.info("An exception {} occurred in the type {} method {}",
                e.getClass().getSimpleName(),
                target.getClass().getSimpleName(),
                jp.getSignature().getName());


        return value;
    }
}
