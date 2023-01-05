package com.nettyDemo.SocketConnect;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
/**
 * author : ZZL(周子林)
 * e-mail : 349709285@qq.com
 * date   : 2019/12/17 16:37
 * desc   :
 * version: 1.0
 */
public class NettyServer {
    private int port = 8888;
    private NettyServerChannelInitializer serverChannelInitializer = null;

    public void bind() throws Exception {
        //配置服务端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            serverChannelInitializer = new NettyServerChannelInitializer();
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    //保持长连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(serverChannelInitializer);
            //绑定端口，同步等待成功
            ChannelFuture f = b.bind(port).sync();
            //等待服务器监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            //释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
