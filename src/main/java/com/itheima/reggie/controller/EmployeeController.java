package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.logging.Logger;


/**
 * @author shkstart
 * @create 2022-08-01 14:59
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    //自动装配EmployeeService

    /**
     * 员工登录
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){//前端传参是一个json数据，需要使用@RequestBody进行接受
        //uername和password被封装进了Employee实体类
        //HttpServletRequest request用于获取session域中的数据
        /*1、将页面提交的密码password进行md5加密处理
         * 2、根据页面提交的用户名username查询数据库
         * 3、如果没有查询到则返回登录失败结果
         * 4、密码比对，如果不一致则返回登录失败结果
         * 5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
         * 6、登录成功，将员工id存入Session并返回登录成功结果
         */
        //1、将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp=  employeeService.getOne(queryWrapper);
        //3、如果没有查询到则返回登录失败结果
        if(emp==null){
            return R.error("登录失败");
        }
        //4、密码比对，如果不一致则返回登录失败结果
        if(!emp.getPassword().equals(password)){
            return R.error("密码错误，登录失败");
        }
        //5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if(emp.getStatus()==0){
            R.error("账号已禁用");
        }
        //6、登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    /**
     * 员工退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清理Seesion中保存的当前登录员工的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 新增员工
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("新增员工，员工信息:{}",employee.toString());

        //设置初始密码123456，需要进行md5加密
       employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
       // employee.setCreateTime(LocalDateTime.now());
        //获取当前创建时间
       // employee.setUpdateTime(LocalDateTime.now());
        //获取当前更新时间
        //获取当前登陆用户的id
      //  Long empId = (Long) request.getSession().getAttribute("employee");
       //employee.setCreateUser(empId);
       // employee.setUpdateUser(empId);
        employeeService.save(employee);
        //此save()方法是继承的IService接口的
        return R.success("新增员工成功");
    }

    /**
     * 员工信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        //页面上需要获取records和total两个属性的值，所以需要使用MyBatisPlus封装的Page方法
        log.info("page={},pageSize={},name={}",page,pageSize,name);
        //1.构造分页构造器
        Page pageinfo=new Page(page,pageSize);
        //2.构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper();
        //添加一个过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        //添加排序条件(根据更新时间来进行降序排序)
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        //3.执行查询
        employeeService.page(pageinfo, queryWrapper);
        return R.success(pageinfo);
    }

    /**
     * 根据id来修改员工信息
     * @param employee
     * @return
     */
    @PutMapping
    //put请求是从客户端向服务端发送数据，但与post不同的是，put真正的目的其实是执行大规模的替换操作，而不是更新操作。
    //put意味着"将数据放到这个URL上"，其本质就是替换已有的数据
    public R<String> update(@RequestBody Employee employee,HttpServletRequest request){
        log.info(employee.toString());
        //获取session域中发起请求的用户信息
        long id = Thread.currentThread().getId();
        log.info("线程id为:{}",id);
       // Long empId = (Long) request.getSession().getAttribute("employee");
        //设置当前更新时间
        //employee.setUpdateTime(LocalDateTime.now());
        //s设置当前更改用户
       // employee.setUpdateUser(empId);
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public  R<Employee> getById(@PathVariable Long id){
        log.info("根据id查询员工信息");
        Employee employee = employeeService.getById(id);

        return R.success(employee);
    }

}
