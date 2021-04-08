package com.cyp.robot.nio.mina;

import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.prefixedstring.PrefixedStringCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by luyijun on 2020/8/2 9:48.
 */
@Slf4j
public class MinaClient {
    private static final String DOMAIN = "127.0.0.1";
    private static final int PORT = 8888;
    static NioSocketConnector connector;
    static IoSession session;


    public static boolean connect() {
        //创建一个socket连接,连接到服务器
        connector = new NioSocketConnector();
        //获取过滤器链,用于添加过滤器
        DefaultIoFilterChainBuilder chain = connector.getFilterChain();
        //添加日志过滤器
        chain.addLast("logger", new LoggingFilter());
        //添加字符的编码过滤器
//        chain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        chain.addLast("codec", new ProtocolCodecFilter(new PrefixedStringCodecFactory(Charset.forName("UTF-8"))));
        //设置消息处理器，用于处理接收到的消息
        connector.setHandler(new MinaClientHandler());
        //根据IP和端口号连接到服务器
        ConnectFuture future = connector.connect(new InetSocketAddress(DOMAIN, PORT));
        // 等待连接创建完成
        future.awaitUninterruptibly();
        boolean connected = future.isConnected();
        System.out.println("isConnected = " + future.isConnected());
        //获取session对象,通过session可以向服务器发送消息
        session = future.getSession();
        session.getConfig().setUseReadOperation(true);
        return connected;
    }


    public static void sendMsg2Service(String message) {
        session.write(message);
    }


    public static boolean close() {
        CloseFuture future = session.getCloseFuture();
        future.awaitUninterruptibly(1000);
        connector.dispose();
        return true;
    }
}
