package com.nettyDemo.helloword;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 通用handler 处理io事件
 */
@ChannelHandler.Sharable
public class HandlerClientHello extends SimpleChannelInboundHandler<ByteBuf> {

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
}
