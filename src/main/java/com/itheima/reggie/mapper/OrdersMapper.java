package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

import java.util.stream.BaseStream;

/**
 * @author shkstart
 * @create 2022-08-21 22:41
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
