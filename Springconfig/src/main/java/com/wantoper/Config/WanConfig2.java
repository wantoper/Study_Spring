package com.wantoper.Config;

import com.wantoper.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WanConfig2 {

    //方法名相当于相当于 bean中的id
    //返回值就是bean 中的 class
    @Bean
    public User getuser(){
        return new User();
    }
}
