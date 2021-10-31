package com.wantoper.serverLog;

import com.sun.org.apache.xml.internal.res.XMLErrorResources_tr;
import org.aspectj.lang.annotation.AdviceName;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Component
@Aspect
public class Afterlog implements AfterReturningAdvice{

    @AdviceName(value = "execution(* com.wantoper.Services.MyServices.*(..))")
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("执行了"+method.getName()+"返回结果为"+returnValue);
    }
}
