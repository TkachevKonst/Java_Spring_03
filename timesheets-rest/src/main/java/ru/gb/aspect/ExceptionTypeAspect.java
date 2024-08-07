package ru.gb.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class ExceptionTypeAspect {

    @Pointcut("execution(* ru.gb.*.*.*.*(..))")
    public void typeExceptionTypePointcut() {
    }

    @Around(value = "typeExceptionTypePointcut()")
    public Object afterThrowingTypeException(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Object target = pjp.getTarget();
        Method method = methodSignature.getMethod();
        Class aClass = method.getReturnType();

        Object value = null;

        if (aClass.isPrimitive()) {
            if (aClass.equals(int.class) || aClass.equals(byte.class) || aClass.equals(short.class)) {
                value = 0;
            } else if (aClass.equals(boolean.class)) {
                value = false;
            } else if (aClass.equals(long.class)) {
                value = 0L;
            } else if (aClass.equals(float.class)) {
                value = 0.0f;
            } else if (aClass.equals(double.class)) {
                value = 0.0d;
            } else {
                value = '\u0000';
            }
        }
        try {
            return pjp.proceed();
        } catch (Exception ex) {
            log.info("An exception {} occurred in the type {} method {}",
                    ex.getClass().getSimpleName(),
                    target.getClass().getSimpleName(),
                    pjp.getSignature().getName());
            return value;
        }
    }
}
