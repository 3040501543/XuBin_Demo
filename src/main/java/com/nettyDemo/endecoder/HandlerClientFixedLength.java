package com.nettyDemo.endecoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 通用handler 处理io事件
 */
@ChannelHandler.Sharable
public class HandlerClientFixedLength extends SimpleChannelInboundHandler<ByteBuf> {

    /**
     * 处理接收到的消息
     * @param channelHandlerContext
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println("接收到消息"+byteBuf.toString(CharsetUtil.UTF_8));
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        /**
         * 处理io所有异常
         */
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd SSS");
        String strDate = sdf.format(new Date());
        ctx.writeAndFlush(Unpooled.copiedBuffer("这是客户端通过active方法中发送的消息"+strDate+"\r\n", CharsetUtil.UTF_8));

    }
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        Channel incoming=ctx.channel();
//        System.out.println("服务器掉线");
////        System.out.println("客户端"+incoming.remoteAddress()+"调用。建立的时候调用一次");
//    }

}
