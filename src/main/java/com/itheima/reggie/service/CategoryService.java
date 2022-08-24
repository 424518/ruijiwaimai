package com.itheima.reggie.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shkstart
 * @create 2022-08-04 23:20
 */

public interface CategoryService extends IService<Category> {

    public void remove(Long id);

    List<Dish> list(LambdaQueryWrapper<DishFlavor> queryWrapper1);
}
