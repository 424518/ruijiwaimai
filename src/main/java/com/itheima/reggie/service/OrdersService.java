package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Orders;

/**
 * @author shkstart
 * @create 2022-08-21 22:43
 */
public interface OrdersService extends IService<Orders> {

    public void submit(Orders orders);
}
