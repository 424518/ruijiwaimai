package com.itheima.reggie.common;

/**
 * @author shkstart
 * @create 2022-08-04 22:05
 *
 * 基于ThreadLocal封装的工具类，用户保存和获取当前登录用户id
 * 作用域：以线程为作用域
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }
    public  static Long getCurrentId(){
        return  threadLocal.get();
    }
}
