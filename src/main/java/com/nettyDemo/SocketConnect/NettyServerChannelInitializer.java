package com.nettyDemo.SocketConnect;


import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;
/**
 * author : ZZL(周子林)
 * e-mail : 349709285@qq.com
 * date   : 2019/12/17 16:37
 * desc   :
 * version: 1.0
 */
public class NettyServerChannelInitializer extends ChannelInitializer {

    private NettyServerHandler handler ;


    @Override
    protected void initChannel(Channel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //解决TCP粘包拆包的问题，以特定的字符结尾（$_）
        pipeline.addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Unpooled.copiedBuffer("$_".getBytes())));
        //字符串解码和编码
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        pipeline.addLast(new IdleStateHandler(40,0,0, TimeUnit.SECONDS));
        //服务器的逻辑
        handler = new NettyServerHandler();
        pipeline.addLast("handler", handler);

    }
}
