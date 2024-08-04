package ru.gb.spring.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Slf4j
@Aspect
@Component
public class TypeArgumentAspect {
    @Pointcut("@annotation(ru.gb.spring.aspect.TypeArgument)")
    public void typeArgumentMethodsPointcut(){}

    @Pointcut("@annotation(ru.gb.spring.aspect.TypeArgument)")
    public void typeArgumentTypePointcut(){}

    @Before(value = "typeArgumentMethodsPointcut() || typeArgumentTypePointcut()")
    public void beforetypeArgumentethods(JoinPoint jp) {
        Object target = jp.getTarget();
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();
        method.getReturnType();
        boolean enabled = true;
        if (method.isAnnotationPresent(TypeArgument.class)){
            enabled = method.getAnnotation(TypeArgument.class).enabled();
        } else if (target.getClass().isAnnotationPresent(TypeArgument.class)) {
            enabled = target.getClass().getAnnotation(TypeArgument.class).enabled();
        }

        if (enabled){
            Object[] nameClassArguments = jp.getArgs();

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < nameClassArguments.length; i++) {
                builder.append("Argumets "+ nameClassArguments[i].getClass().getSimpleName() + " - Value: "+ nameClassArguments[i].toString()+"\n");
            }
            log.info("Type {} , Method {}: {}", target.getClass().getSimpleName(),jp.getSignature().getName(), builder);

        }



    }
}
