package com.minioTest;

import io.minio.ObjectWriteResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@SpringBootTest
public class MinIOTest {

    @Resource
    MinIOTemplate minTemplate;

    //测试创建bucket
    @Test
    void testCreateBucket() throws Exception {
        minTemplate.makeBucket("test");
    }

    //测试上传文件对象
    @Test
    void testPutObject() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

        String saveFileName=UUID.randomUUID().toString()+dateFormat.format(new Date()).toString();

        ObjectWriteResponse response = minTemplate.putObject("test",
                "base/"+saveFileName+".png",
                "D:\\Backup\\Documents\\WeChat Files\\wxid_0fmxcpkpcfaa22\\FileStorage\\Video\\2022-03\\13263adc4acd58ac892204b8a8061f0a.jpg");
        System.out.println(response.object());
    }

    //测试删除文件对象
    @Test
    void testDeleteObject() throws Exception {
        minTemplate.removeObject("test",
                "base/springboot青铜到王者封面.png");
    }
}