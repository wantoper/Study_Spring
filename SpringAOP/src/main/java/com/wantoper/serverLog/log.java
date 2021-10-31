package com.wantoper.serverLog;

import org.aspectj.lang.ProceedingJoinPoint;

public class log {
    public void firstlog(){
        System.out.println("firstlog");
    }

    public void lastlog(){
        System.out.println("lastlog");
    }

    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("start log:" + joinPoint.getSignature().getName());
        Object object = joinPoint.proceed();
        System.out.println("end log:" + joinPoint.getSignature().getName());
        return object;
    }
}
