//package ru.gb.spring.aspect;
//
//
//import lombok.extern.slf4j.Slf4j;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Aspect;
//
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Method;
//
//@Slf4j
//@Aspect
//@Component
//public class ExceptionTypeAspect {
//
//    @Pointcut("@annotation(ru.gb.spring.aspect.ExceptionType)")
//    public void typeExceptionMethodsPointcut(){}
//
//    @Pointcut("@annotation(ru.gb.spring.aspect.ExceptionType)")
//    public void typeExceptionTypePointcut(){}
//
//    @After(value = "typeExceptionMethodsPointcut() || typeExceptionTypePointcut()")
//    public void afterTypeExctption(ProceedingJoinPoint pjp, Exception e) throws Throwable {
//        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
//        Object target = pjp.getTarget();
//        Method method = methodSignature.getMethod();
//        Object o;
//
//        boolean enabled = true;
//        if (method.isAnnotationPresent(Timer.class)){
//            enabled = method.getAnnotation(Timer.class).enabled();
//        } else if (target.getClass().isAnnotationPresent(Timer.class)) {
//            enabled = target.getClass().getAnnotation(Timer.class).enabled();
//        }
//        if (enabled){
//            try {
//                pjp.proceed();
//            }finally {
//                log.info("An exception {} occurred in the type {} method {}", e.getClass().getSimpleName() , target.getClass().getSimpleName(), pjp.getSignature().getName());
//
//            }
//        }
//    }
//}
