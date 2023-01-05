package com.nettyDemo.endecoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 服务器端io处理类，连接监测
 */
public class HandlerServerEndeCoder extends ChannelInboundHandlerAdapter {
    public static ChannelGroup channels= new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


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


    /**
     * 新增连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming=ctx.channel();
        System.out.println("客户端"+incoming.remoteAddress()+"已连接上来");
        channels.add(incoming);
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming=ctx.channel();
        System.out.println("客户端"+incoming.remoteAddress()+"已断开");
        channels.remove(incoming);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming=ctx.channel();
        System.out.println("客户端"+incoming.remoteAddress()+"在线。建立的时候调用一次");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd SSS");
        String strDate = sdf.format(new Date());
        ctx.writeAndFlush(Unpooled.copiedBuffer("这是服务器端在active方法中反馈的消息"+strDate+"\r\n", CharsetUtil.UTF_8));

    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming=ctx.channel();
        System.out.println("客户端"+incoming.remoteAddress()+"关闭。关闭的时候调用一次");
    }


}
