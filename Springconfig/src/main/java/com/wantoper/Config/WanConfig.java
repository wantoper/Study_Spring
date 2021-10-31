package com.wantoper.Config;

import com.wantoper.bean.Fruit;
import com.wantoper.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

//相当于<benas>
@Configuration
//引入
@Import(WanConfig2.class)
public class WanConfig {
    //方法名相当于相当于 bean中的id
    //返回值就是bean 中的 class
    @Bean
    public User getuser(Fruit fff){
        User user = new User();
        user.setName("big Ming");
        user.setFruit(fff);
        return user;
    }
    @Bean
    public Fruit fff(){
        Fruit fruit = new Fruit();
        fruit.setId(1);
        fruit.setName("香蕉");
        fruit.setPrice(1.8);
        return fruit;
    }

    @Bean
    public Fruit ffff(){
        Fruit fruit = new Fruit();
        fruit.setId(2);
        fruit.setName("Apple");
        fruit.setPrice(2.8);
        return fruit;
    }


}
