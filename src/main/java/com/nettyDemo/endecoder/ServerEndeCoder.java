package com.nettyDemo.endecoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class ServerEndeCoder {

    private int port;

    public ServerEndeCoder(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        //客户端的连接 负责接收
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //负责处理消息io
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bs = new ServerBootstrap();
            bs.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)//实例化一个通道
                    .localAddress(new InetSocketAddress(port))//设置监听端口
                    .option(ChannelOption.SO_BACKLOG,128)//最大保持连接数128，option主要针对boss线程组
                    .childOption(ChannelOption.SO_KEEPALIVE,true)//启用心跳保活机制，childOption主要针对worker线程组
                    .childHandler(new ChannelInitializer<SocketChannel>() {//进行通道初始化配置
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new HandlerServerEndeCoder());//添加自定义的handler
                        }
                    });
            //连接远程节点；等待连接完成
            ChannelFuture future = bs.bind().sync();

            System.out.println("在" + future.channel().localAddress() + "上开启监听");

            //阻塞操作 closeFuture()开启了一个channel的监听器
            future.channel().closeFuture().sync();


        }finally {
            bossGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
        new ServerEndeCoder(18080).run();
    }

}
