package com.cyp.robot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableCaching
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.cyp.mapper")
@EnableAsync
public class AiRobotApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiRobotApplication.class, args);
    }


}
