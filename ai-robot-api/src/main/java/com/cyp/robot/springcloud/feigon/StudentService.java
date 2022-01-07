package com.cyp.robot.springcloud.feigon;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "EUREKA-PROVIDER") //开启Feign客户端，HELLO-SERVICE是client-user微服务的serviceId
public interface StudentService {

    //对应要调用的client-user微服务控制层请求方法
    @RequestMapping("/hello")
    String hello();

    //对应要调用的client-user微服务控制层请求方法
    @RequestMapping("/offLine")
    String offLine();

}
