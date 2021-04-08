package com.cyp.robot.nio.netty.service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by luyijun on 2020/10/26 21:47.
 */
@Component
@Slf4j
public class NettyServiceInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
        pipeline.addLast(new LengthFieldPrepender(4));
        //字符串解码
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        //字符串编码
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        //自己定义的处理器
        pipeline.addLast(new NettyServerHandler());
    }


    /**
     * 启动netty服务端
     */
    @PostConstruct
    public void init() {
        new Thread(() -> {
            log.info("TestNettyService启动netty 开始..............");
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup wokreGroup = new NioEventLoopGroup();
            try {
                ServerBootstrap serverBootstrap = new ServerBootstrap();
                //在服务器端的handler()方法表示对bossGroup起作用，而childHandler表示对wokerGroup起作用
                serverBootstrap.group(bossGroup, wokreGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new NettyServiceInitializer());
                ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
                channelFuture.channel().closeFuture().sync();
                log.info("TestNettyService启动netty 成功，端口=8899.........");
            } catch (InterruptedException e) {
                log.info("TestNettyService启动netty 异常.........");
                e.printStackTrace();
            } finally {
                bossGroup.shutdownGracefully();
                wokreGroup.shutdownGracefully();
            }
        }).start();
    }


}
