package com.cyp.robot.netty.service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by Jun on 2020/10/26 21:46.
 */
public class TestNettyService {
    public static void main(String[] args) throws Exception {
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
        } finally {
            bossGroup.shutdownGracefully();
            wokreGroup.shutdownGracefully();
        }
    }
}
