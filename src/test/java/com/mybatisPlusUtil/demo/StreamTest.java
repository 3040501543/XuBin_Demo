package com.mybatisPlusUtil.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class StreamTest {

    @Test
    public void steamtest01(){
        List<String> words = Arrays.asList("hello","word");
        for (String word : words) {

            System.out.println(word.split(""));
        }
    }

    public static void main(String[] args) {
        List<String> words = Arrays.asList("hello","word");
        for (String word : words) {
            String[] split = word.split("");;
            for (int i = 0; i < split.length; i++) {
//                System.out.println(split[i]);
            }
        }
        List<String> collect = words.stream().flatMap(w -> Arrays.stream(w.split("")))
                .collect(Collectors.toList());
//        System.out.println(collect.toString());


        List<String> cities = Arrays.asList(
                "Milan",
                "london",
                "San Francisco",
                "Tokyo",
                "New Delhi"
        );
        System.out.println(cities);
//[Milan, london, San Francisco, Tokyo, New Delhi]

        //（字母大小写不敏感）的规则排序 字母排序
        cities.sort(String.CASE_INSENSITIVE_ORDER);
        System.out.println(cities);
//[london, Milan, New Delhi, San Francisco, Tokyo]
        //字母自然顺序排序
        cities.sort(Comparator.naturalOrder());
        System.out.println(cities);
//[Milan, New Delhi, San Francisco, Tokyo, london]

    }



}
