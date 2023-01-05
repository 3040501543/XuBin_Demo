package com.nettyDemo.helloword;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class HandlerServerHello extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("收到客户端发送过来的消息："+in.toString(CharsetUtil.UTF_8));

        //写入并发送短信到远程（客户端）
        ctx.writeAndFlush(Unpooled.copiedBuffer("你好，已经收到你发送的消息",CharsetUtil.UTF_8));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
