package com.wantoper.Services;


import org.springframework.stereotype.Component;

@Component("autoServices")
public class AutoServices implements Serverices{
    @Override
    public void add() {
        System.out.println("自动注解的方式增加了一个用户");
    }

    @Override
    public void remove() {
        System.out.println("自动注解的方式删除了一个用户");
    }

    @Override
    public void update() {
        System.out.println("自动注解的方式修改了一个用户");
    }

    @Override
    public void select() {
        System.out.println("自动注解的方式查找了一个用户");
    }
}
