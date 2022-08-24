package com.itheima.reggie.common;

/**
 * @author shkstart
 * @create 2022-08-02 10:37
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
//可以编写可应用于各种控制器的全局代码-从所有控制器到选定的包，甚至是特定的注释
@ResponseBody//将java对象转为json格式的数据。
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 异常处理方法
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
   public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
    log.error(ex.getMessage());
    if(ex.getMessage().contains("Duplicate entry")){
        String[] split = ex.getMessage().split("");
        String msg= split[2] +"已存在";
        return R.error(msg);
    }
    return R.error("未知错误");
   }

    /**
     * 异常处理方法
     * @return
     */
    @ExceptionHandler(CustomerException.class)
    public R<String> exceptionHandler(CustomerException ex){
        log.error(ex.getMessage());
        return R.error(ex.getMessage());
    }

}
