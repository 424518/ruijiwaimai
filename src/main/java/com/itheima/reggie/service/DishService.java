package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;
import org.springframework.stereotype.Service;

/**
 * @author shkstart
 * @create 2022-08-05 11:47
 */

public interface DishService extends IService<Dish> {

    //新增菜品，同时驶入菜品对应的口味数据，需要操作两张表：dish、dish_flavor
    public void saveWithFlavor(DishDto dishDto);
    //根据id查询菜品信息和对应的口味信息
    public DishDto getByIdWithFlavor(Long id);
    //更新菜品信息以及对应的口味信息
    public void updateWithFlavor(DishDto dishDto);
}
