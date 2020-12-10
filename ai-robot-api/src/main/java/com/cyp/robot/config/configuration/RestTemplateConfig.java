//package com.cyp.robot.config.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.client.ClientHttpRequestFactory;
//import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.http.client.SimpleClientHttpRequestFactory;
//import org.springframework.web.client.RestTemplate;
//
//import javax.annotation.Resource;
//import java.util.Collections;
//
///**
// * <p>RestTemplate配置类</p>
// */
//@Configuration
//public class RestTemplateConfig {
//
//    @Resource
//    private ClientHttpRequestInterceptor loggingClientHttpRequestInterceptor;
//
//    @Bean
//    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
//        RestTemplate restTemplate = new RestTemplate(factory);
//        restTemplate.setInterceptors(Collections.singletonList(loggingClientHttpRequestInterceptor));
//        return restTemplate;
//    }
//
//    @Bean
//    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
//        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
//        factory.setReadTimeout(5000);
//        factory.setConnectTimeout(5000);
//        return factory;
//    }
//}