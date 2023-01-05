package com.nettyDemo.helloword;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * 客户端启动类
 */
public class DemoClientHello {

    private final String host;
    private final int port;

    public DemoClientHello(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 配置相应的参数，提供连接到远程的方法
     * @throws Exception
     */
    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();//io线程池
        try {
            Bootstrap bs = new Bootstrap();
            bs.group(group)
                    .channel(NioSocketChannel.class)//实例化一个通道
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer<SocketChannel>() {//进行通道初始化配置
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new HandlerClientHello());//添加自定义的handler
                        }
                    });
            //连接远程节点；等待连接完成
            ChannelFuture future=bs.connect().sync();
            //发送一个字符串到服务器端
            future.channel().writeAndFlush(new String("Hello World!"));
            future.channel().writeAndFlush(Unpooled.copiedBuffer("Hello World222", CharsetUtil.UTF_8));
            //阻塞操作 closeFuture()开启了一个channel的监听器
            future.channel().closeFuture().sync();

        } finally {
            //优雅 关闭线程池
            group.shutdownGracefully().sync();
        }

    }

    public static void main(String[] args) throws Exception {
        new DemoClientHello("127.0.0.1", 18080).run();
    }

}
