package ru.gb.spring.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Slf4j
@Aspect
@Component
public class LoggingAspect {
    @Pointcut("@annotation(ru.gb.spring.aspect.TypeArgument)")
    public void typeArgumentMethodsPointcut() {
    }

    @Pointcut("@annotation(ru.gb.spring.aspect.TypeArgument)")
    public void typeArgumentTypePointcut() {
    }

    @Pointcut("execution(* ru.gb.*.*.*.*(..))")
    public void typeExceptionTypePointcut() {
    }

    @Before(value = "typeArgumentMethodsPointcut() || typeArgumentTypePointcut()")
    public void beforetypeArgumentethods(JoinPoint jp) {
        Object target = jp.getTarget();
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();
        method.getReturnType();
        boolean enabled = true;
        if (method.isAnnotationPresent(TypeArgument.class)) {
            enabled = method.getAnnotation(TypeArgument.class).enabled();
        } else if (target.getClass().isAnnotationPresent(TypeArgument.class)) {
            enabled = target.getClass().getAnnotation(TypeArgument.class).enabled();
        }
        if (enabled) {
            Object[] nameClassArguments = jp.getArgs();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < nameClassArguments.length; i++) {
                builder.append("Argumets " + nameClassArguments[i].getClass().getSimpleName() + " - Value: " + nameClassArguments[i].toString() + "\n");
            }
            log.info("Type {} , Method {}: {}", target.getClass().getSimpleName(), jp.getSignature().getName(), builder);
        }
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
