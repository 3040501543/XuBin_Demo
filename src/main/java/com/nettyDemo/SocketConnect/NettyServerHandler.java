package com.nettyDemo.SocketConnect;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Date;
import java.util.HashMap;

/**
 * author : ZZL(周子林)
 * e-mail : 349709285@qq.com
 * date   : 2019/12/17 16:37
 * desc   :
 * version: 1.0
 */
public class NettyServerHandler extends SimpleChannelInboundHandler {

    //新建一个channelGroup，用于存放连接的channel
    public static HashMap online_channels = new HashMap();
    //记录每一个channel的心跳包丢失次数
    public HashMap online_channels_heart = new HashMap();

    public static ChannelFuture sendMsg2Client(String id, String msg) {
        ChannelHandlerContext ctx = (ChannelHandlerContext) NettyServerHandler.online_channels.get(id);
        if (null == ctx) {
            System.out.println("Channel is not exist");
            return null;
        }
        //返回ChannelFuture ，异步回调结果，若消息发送失败，则继续发送
        //若要保证数据的完整性，则需要做MD5数据校验
        ChannelFuture future = ctx.channel()
                .writeAndFlush(msg.concat("$_"));
        return future;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("RemoteAddress : " + ctx.channel().remoteAddress().toString() + " add !");
        online_channels.put(ctx.channel().id().asLongText(), ctx);
        online_channels_heart.put(ctx.channel().id().asLongText(), 0);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("RemoteAddress : " + ctx.channel().remoteAddress().toString() + " remove !");
        online_channels.remove(ctx.channel().id().asLongText());
        online_channels_heart.remove(ctx.channel().id().asLongText());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        System.out.println("Client: " + channelHandlerContext.channel().id().asLongText() + " say : " + o.toString());
        System.out.println(new Date());
        //add channel（重连）
        online_channels.putIfAbsent(channelHandlerContext.channel().id().asLongText(), channelHandlerContext);
        //重置心跳丢失次数
        online_channels_heart.replace(channelHandlerContext.channel().id().asLongText(), 0);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("RemoteAddress : " + ctx.channel().remoteAddress().toString() + " active !");
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                // 空闲40s之后触发 (心跳包丢失)
                Integer counter = (Integer) online_channels_heart.get(ctx.channel().id().asLongText());
                if (counter >= 3) {
                    // 连续丢失3个心跳包 (断开连接)
                    ctx.channel().close().sync();
                    System.out.println("已与" + ctx.channel().remoteAddress() + "断开连接");
                } else {
                    counter++;
                    //重置心跳丢失次数
                    online_channels_heart.replace(ctx.channel().id().asLongText(), counter);
                    System.out.println("丢失了第 " + counter + " 个心跳包");
                }
            }
        }
    }
}
