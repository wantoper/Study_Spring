package com.wantoper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("h")
public class Hello{

    @Value("sss")
    String hello="Hello World!";

//    @Autowired
    @Resource(name="frt")
    Fruit fruit;

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }

    public void getHello(){
        System.out.println(hello);
    }
    public void setHello(String s){
        this.hello=s;
    }
}
