package com;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import cn.hutool.socket.nio.NioServer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @Author xubin
 * @Date 2022/10/18 11:50
 * @Version
 **/

@Component
public class RunServer implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        NioServer server = new NioServer(18888);
        server.setChannelHandler((sc)->{
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            try{
                //从channel读数据到缓冲区
                int readBytes = sc.read(readBuffer);
                if (readBytes > 0) {
                    //Flips this buffer.  The limit is set to the current position and then
                    // the position is set to zero，就是表示要从起始位置开始读取数据
                    readBuffer.flip();
                    //eturns the number of elements between the current position and the  limit.
                    // 要读取的字节长度
                    byte[] bytes = new byte[readBuffer.remaining()];
                    //将缓冲区的数据读到bytes数组
                    readBuffer.get(bytes);
                    String body = StrUtil.utf8Str(bytes);
                    Console.log("[{}]: {}", sc.getRemoteAddress(), body);
                } else if (readBytes < 0) {
                    IoUtil.close(sc);
                }
            } catch (IOException e){
                throw new IORuntimeException(e);
            }
        });
        server.listen();


    }
}
