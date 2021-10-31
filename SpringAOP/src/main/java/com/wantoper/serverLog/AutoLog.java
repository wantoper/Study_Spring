package com.wantoper.serverLog;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AutoLog {

    @Before(value = "execution(* com.wantoper.Services.AutoServices.*(..))")
    public void firstlog(){
        System.out.println("firstlog");
    }

    @After(value = "execution(* com.wantoper.Services.AutoServices.*(..))")
    public void lastlog(){
        System.out.println("lastlog");
    }

    @Around(value = "execution(* com.wantoper.Services.AutoServices.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("start log:" + joinPoint.getSignature().getName());
        Object object = joinPoint.proceed();
        System.out.println("end log:" + joinPoint.getSignature().getName());
        return object;
    }
}
