package ru.gb.spring.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
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

    @Pointcut("execution(* ru.gb.spring.service.TimesheetService.*(..))")
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
