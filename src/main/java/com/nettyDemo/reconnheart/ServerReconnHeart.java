package com.nettyDemo.reconnheart;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

public class ServerReconnHeart {

    private int port;

    public ServerReconnHeart(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();//io线程池
        try {
            ServerBootstrap bs = new ServerBootstrap();
            bs.group(group)
                    .channel(NioServerSocketChannel.class)//实例化一个通道
                    .localAddress(new InetSocketAddress(port))//设置监听端口
                    .childHandler(new ChannelInitializer<SocketChannel>() {//进行通道初始化配置
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("decoder", new StringDecoder());
                            socketChannel.pipeline().addLast("encoder", new StringEncoder());
                            socketChannel.pipeline().addLast(new HandlerServerReconnHeart());//添加自定义的handler
                        }
                    });
            //连接远程节点；等待连接完成
            ChannelFuture future = bs.bind().sync();

            System.out.println("在" + future.channel().localAddress() + "上开启监听");

            //阻塞操作 closeFuture()开启了一个channel的监听器
            future.channel().closeFuture().sync();


        }finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        new ServerReconnHeart(8888).run();
    }

}
