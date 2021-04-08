package com.cyp.robot.config.webScoket;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Created by luyijun on 2020/9/22 1:40.
 */

@Component
public class WebSocketConfig {

    /**
     * ServerEndpointExporter 作用
     * <p>
     * 这个Bean会自动注册使用@ServerEndpoint注解声明的websocket endpoint
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}