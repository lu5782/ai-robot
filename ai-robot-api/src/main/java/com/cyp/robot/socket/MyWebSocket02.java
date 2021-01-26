package com.cyp.robot.socket;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;

/**
 * Created by Jun on 2020/9/19 16:21.
 */
@Component
public class MyWebSocket02 {
    private static final String DOMAIN = "127.0.0.1";
    private static final int PORT = 8888;

    @PostConstruct
    public void init() {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(DOMAIN, PORT);

        WebSocketServer socket = new WebSocketServer(inetSocketAddress) {
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
        System.out.println("websocket启动成功....................");
    }


}
