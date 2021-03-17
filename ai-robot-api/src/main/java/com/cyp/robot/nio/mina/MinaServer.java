package com.cyp.robot.nio.mina;

import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.prefixedstring.PrefixedStringCodecFactory;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * Created by Jun on 2020/8/2 10:03.
 */
@Slf4j
public class MinaServer {
    private static final String DOMAIN = "127.0.0.1";
    private static final int PORT = 8888;


    public static void connect() {
        //监听传入连接的对象
        IoAcceptor acceptor = new NioSocketAcceptor();
        //记录所有的信息，比如创建session(会话)，接收消息，发送消息，关闭会话等
//        acceptor.getFilterChain().addLast("logger", new LoggingFilter());

        //ProtocolCodecFilter(协议编解码过滤器).这个过滤器用来转换二进制或协议的专用数据到消息对象中， 反之亦然。
        TextLineCodecFactory textLineCodecFactory =
                new TextLineCodecFactory(StandardCharsets.UTF_8,
                        LineDelimiter.WINDOWS.getValue(), LineDelimiter.WINDOWS.getValue());
        ProtocolCodecFilter protocolCodecFilter = new ProtocolCodecFilter(textLineCodecFactory);
        acceptor.getFilterChain().addLast("protocolCodecFilter", protocolCodecFilter);

        acceptor.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new PrefixedStringCodecFactory(StandardCharsets.UTF_8)));
        // 我们这里使用一个已经存在的TextLine工厂，因为我们这里只处理一些文字消息（你不需要再去写编解码部分）。
        //创建一个handler来实时处理客户端的连接和请求，这个handler 类必须实现 IoHandler这个接口。
        acceptor.setHandler(new MinaServerHandler());
        acceptor.getSessionConfig().setReadBufferSize(2048);
        // 心跳
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        // 绑定端口,启动服务，并开始处理远程客户端请求
        try {
            acceptor.bind(new InetSocketAddress(DOMAIN, PORT));
        } catch (IOException e) {
            e.printStackTrace();
            log.error("服务端启动失败");
        }
        log.info("服务端启动成功");
    }

}
