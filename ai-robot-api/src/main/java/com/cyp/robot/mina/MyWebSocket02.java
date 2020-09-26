package com.cyp.robot.mina;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;

/**
 * Created by Jun on 2020/9/19 16:21.
 */
//@Component
public class MyWebSocket02 {


    @PostConstruct
    public void init() {
        WebSocketServer socket = new WebSocketServer(new InetSocketAddress(8888)) {
            @Override
            public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
                System.out.println("onOpen ===" + webSocket.getRemoteSocketAddress().getAddress().getHostAddress());
            }

            @Override
            public void onClose(WebSocket webSocket, int i, String s, boolean b) {
                System.out.println("onClose ===" + webSocket.getRemoteSocketAddress().getAddress().getHostAddress());
            }

            @Override
            public void onMessage(WebSocket webSocket, String s) {
                System.out.println("you have a new message: " + s);
                //向客户端发送消息
                webSocket.send(s);
            }

            @Override
            public void onError(WebSocket webSocket, Exception e) {
                System.out.println("onError ===" + webSocket.getRemoteSocketAddress().getAddress().getHostAddress());
            }

            @Override
            public void onStart() {
                System.out.println("onStart ===");
            }
        };
        socket.start();
        System.out.println("websocket 启动成功....................");
    }


}
