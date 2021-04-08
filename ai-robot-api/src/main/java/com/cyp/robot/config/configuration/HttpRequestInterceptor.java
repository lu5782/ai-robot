package com.cyp.robot.config.configuration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by luyijun on 2020/3/18 11:20.
 * 很多请求都需要用到相同的Http Header。
 * Spring提供了ClientHttpRequestInterceptor接口，可以对请求进行拦截，
 * 并在其被发送至服务端之前修改请求或是增强相应的信息。
 */
@Component
public class HttpRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        HttpHeaders headers = httpRequest.getHeaders();
        // 加入自定义字段
        headers.add("actionId", "miko");
        // 保证请求继续被执行
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
