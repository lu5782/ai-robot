package com.cyp.robot.nio.netty.service;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * Created by luyijun on 2020/10/26 21:48.
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        //打印出客户端地址
        System.out.println("客户端地址= " + channelHandlerContext.channel().remoteAddress());
        System.out.println("接收到客户端消息= " + s);
        channelHandlerContext.channel().writeAndFlush("form server : " + UUID.randomUUID());
        System.out.println("");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
