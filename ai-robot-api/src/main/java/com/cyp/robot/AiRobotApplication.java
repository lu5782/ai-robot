package com.cyp.robot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling   // 开启定时任务
@EnableCaching
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.cyp.mapper")
@EnableAsync
@EnableFeignClients
public class AiRobotApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiRobotApplication.class, args);
    }


}
