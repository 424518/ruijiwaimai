package com.itheima.test;

import org.junit.jupiter.api.Test;

/**
 * @author shkstart
 * @create 2022-08-07 21:59
 */
public class UploadFileTest {
    @Test
    public void test1(){
        String fileName ="errorwe.jpg";
        String suffix=fileName.substring(fileName.lastIndexOf("."));
        System.out.println(suffix);
    }
}
