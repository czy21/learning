package com.learning;


import com.learning.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@MapperScan(basePackageClasses = UserMapper.class)
@SpringBootApplication
public class StartupApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartupApplication.class, args);
    }
}