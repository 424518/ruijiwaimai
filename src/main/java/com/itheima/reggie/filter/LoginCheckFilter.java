package com.itheima.reggie.filter;

/**
 * @author shkstart
 * @create 2022-08-01 21:37
 */

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *解决为登录就可以访问的问题
 * 检查用户是否已经完成登录
 * 可以使用过滤器或者拦截器，在过滤器或者拦截器中进行判断
 */
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher  PATH_MATCHER=new AntPathMatcher();
    //主要用来进行路径匹配的匹配器，支持通配符
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //1.获取本次请求的URL
        String requestURI = request.getRequestURI();// /backend/index.html
        log.info("拦截到请求:{}",requestURI);
        String[] urls = new String[]{
                "/employee/login", //登录系统界面
                "/employee/logout", //退出界面
                "/backend/**",//静态资源，主要是拦截动态请求及数据
                "/front/**" ,   //前端界面资源
                "/common/**",//文件上传
                "user/sendMsg",//移动端发送短信
                "/user/login"//移动端登录
        };
        //2.判断本次请求是否需要处理
        boolean check = check(urls, requestURI);
        //3.如果不需要处理，则直接放行
        if(check){
            log.info("本次请求{}不需要处理",requestURI);
            filterChain.doFilter(request,response);
            return;
        }
        //4-1.判断员工登录状态，如果已经登录，则直接放行
        if(request.getSession().getAttribute("employee") != null){
            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("employee"));
            Long empId=(Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            long id = Thread.currentThread().getId();
            log.info("线程id为:{}",id);
            filterChain.doFilter(request,response);
            return;
        }
        //4-2.判断移动端用户登录状态，如果已经登录，则直接放行
        if(request.getSession().getAttribute("user") != null){
            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("user"));
            Long userId=(Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            long id = Thread.currentThread().getId();
            log.info("线程id为:{}",id);
            filterChain.doFilter(request,response);
            return;
        }
        log.info("用户未登录");
        //5.如果未登录则返回登录页，通过输出流方式向客户端响应数据
        //前端页面引用request.js页面，其中含有响应拦截器，需要以code属性的值进行判断，所以只需要将code赋值并添加msg
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
//        log.info("拦截到请求:{}",request.getRequestURI());
    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param requestURL
     * @param urls
     * @return
     */
    public  boolean check(String[] urls,String requestURL){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURL);
            //返回true表示匹配上了，返回false就表示未匹配成功
            if(match){//匹配成功
                return true;
            }
        }
        return false;
    }

}
