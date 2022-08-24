package com.itheima.reggie.controller;

import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleCreateTableStatement;
import com.itheima.reggie.ReggieApplication;
import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @author shkstart
 * @create 2022-08-06 16:43
 * 文件上传和下载
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    //声明一个变量来进行获取配置文件中的临时文件转存的地址
    @Value("${reggie.path}")
    private  String basePath;
    /**
     * 文件上传功能
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        //file是一个临时文件，需要转存到指定位置，否则本次请求完成后临时文件会被删除
        log.info(file.toString());

        //原始文件名称
        String originalFilename = file.getOriginalFilename();
        //动态截取原始文件名的后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //使用UUID重新生成文件名，防止文件名称重复造成文件覆盖
        String fileName= UUID.randomUUID().toString()+suffix;
        //创建一个目录对象
        File dir=new File(basePath);
        //判断当前目录是否存在
        if(!dir.exists()){
            //目录不存在，需要创建
            dir.mkdir();
        }
        try { //将临时文件转存到指定位置
            file.transferTo(new File(basePath+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(fileName);

    }

    /**
     * 文件下载
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){


        try {
            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream=new FileInputStream(new File(basePath+name));
            //输出流，通过输出流将文件协会浏览器，早浏览器展示图片了
            ServletOutputStream outputStream=response.getOutputStream();
            //设置输出的文件格式
            response.setContentType("reggiewenjian/jpeg");
            int lens=0;
            byte[] bytes=new byte[1024];
            //lens等于-1就说明已经读取完毕
            while ((lens=fileInputStream.read(bytes))!=-1){
               outputStream.write(bytes,0,lens);
               outputStream.flush();
            }
            //关闭资源
            outputStream.close();
            fileInputStream.close();
            } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
