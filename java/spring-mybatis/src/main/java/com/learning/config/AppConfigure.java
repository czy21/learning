package com.learning.config;

import com.clearning.entity.po.UserPO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfigure {

    @Bean
    public UserPO userPOBean() {
        return new UserPO();
    }
}
