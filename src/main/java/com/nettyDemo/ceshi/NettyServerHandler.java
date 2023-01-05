package com.nettyDemo.ceshi;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 空闲次数
     */
    private int idle_count = 1;
    /**
     * 发送次数
     */
    private int count = 1;
    /**
     * A thread-safe Set  Using ChannelGroup, you can categorize Channels into a meaningful group.
     * A closed Channel is automatically removed from the collection,
     */
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);





    /**
     * 客户端连接会触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        log.info("NettyClient:" + incoming.remoteAddress() + "在线");
    }

    /**
     * 客户端发消息会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("服务器收到消息: {}", msg.toString());
        System.out.println("进入到channelRead");
        System.out.println("收到消息:"+msg.toString());
        ctx.write("你也好哦");
        ctx.writeAndFlush(msg+"+writeAndFlush");
        ctx.flush();
    }


    /**
     * 发生异常触发
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming = ctx.channel();
        log.info("NettyClient:" + incoming.remoteAddress() + "异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 超时处理，如果5秒没有收到客户端的心跳，就触发; 如果超过两次，则直接关闭;
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
        Channel incoming = ctx.channel();
        if (obj instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) obj;
            // 如果读通道处于空闲状态，说明没有接收到心跳命令
            if (IdleState.READER_IDLE.equals(event.state())) {
                if (idle_count > 2) {
                    log.info("超过两次无客户端请求，关闭该channel");
                    ctx.channel().close();
                }

                log.info("已等待5秒还没收到客户端发来的消息");
                idle_count++;
            }
        } else {
            super.userEventTriggered(ctx, obj);
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();

        // Broadcast a message to multiple Channels
        channels.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 加入\n");

        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();

        // Broadcast a message to multiple Channels
        channels.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 离开\n");

        // A closed Channel is automatically removed from ChannelGroup,
        // so there is no need to do "channels.remove(ctx.channel());"
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }
}
