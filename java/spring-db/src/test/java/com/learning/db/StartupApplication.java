package com.learning.db;


import com.learning.db.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

//@EnableWebMvc
@Configuration
@MapperScan(basePackageClasses = UserMapper.class)
@SpringBootApplication
public class StartupApplication {

    public static void main(String[] args) {
        SpringApplication.run(DBApplication.class, args);
    }
}
