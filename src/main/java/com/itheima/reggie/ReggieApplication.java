package com.itheima.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author shkstart
 * @create 2022-08-01 8:48
 */
@Slf4j
//加上可以使用log日志
@SpringBootApplication
//标注springboot启动类需要设置
@ServletComponentScan
//开启事务支持
@EnableTransactionManagement
/*
SpringBootApplication 上使用@ServletComponentScan 注解后
Servlet可以直接通过@WebServlet注解自动注册
Filter可以直接通过@WebFilter注解自动注册
Listener可以直接通过@WebListener 注解自动注册
 */
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class,args);
        log.info("项目启动成功.......");
    }
}
