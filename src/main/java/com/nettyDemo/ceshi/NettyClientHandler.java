package com.nettyDemo.ceshi;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    //private ResponseResult responseResult;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端Active .....");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("收到服务器的消息");
        log.info("客户端收到消息: {}", msg.toString());
        //this.responseResult = ResponseResult.success(msg.toString(), CouponTypeEnum.OPERATE_SUCCESS.getCouponTypeDesc());
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
