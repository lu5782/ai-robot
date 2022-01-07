package com.cyp.robot.springcloud.ribbon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by luyijun on 2020/3/9 11:16.
 */
@Configuration
public class RibbonConfig {
    @Bean
//    @LoadBalanced // 这个注解是实现负载均衡的，如果不写的话，就只能直接通过url访问其他服务
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
