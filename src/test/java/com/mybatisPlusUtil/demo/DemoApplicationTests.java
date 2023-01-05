package com.mybatisPlusUtil.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class DemoApplicationTests {
    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        BigDecimal a = new BigDecimal (101);
        BigDecimal b = new BigDecimal (111);

//使用compareTo方法比较
//注意：a、b均不能为null，否则会报空指针
        if(a.compareTo(null) == -1){
            System.out.println("a小于b");
        }
    }

}
