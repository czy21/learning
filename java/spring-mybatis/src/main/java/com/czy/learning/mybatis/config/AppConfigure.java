package com.czy.learning.mybatis.config;

import com.czy.learning.domain.entity.po.UserPO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfigure {

    @Bean
    public UserPO userPOBean() {
        return new UserPO();
    }
}
