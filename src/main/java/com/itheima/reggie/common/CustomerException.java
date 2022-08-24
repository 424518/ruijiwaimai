package com.itheima.reggie.common;

/**
 * 自定义业务异常类
 * @author shkstart
 * @create 2022-08-05 14:14
 */

public class CustomerException extends RuntimeException{

    public CustomerException(String message){
        super(message);
    }

}
