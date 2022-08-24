package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.CustomerException;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.entity.SetmealDish;
import com.itheima.reggie.mapper.SetmealMapper;
import com.itheima.reggie.service.SetmealDishService;
import com.itheima.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shkstart
 * @create 2022-08-05 11:48
 */
@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService{
    @Autowired
     private SetmealDishService setmealDishService;
    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    @Transactional
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐的基本信息，操作setmeal，执行insert操作
        this.save(setmealDto);
        //里边只有dishid，没有setmealid
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect((Collectors.toList()));
        //保存套餐和菜品的关联信息，操作setmeal_dish，执行insert操作
         setmealDishService.saveBatch(setmealDishes);
    }
    /**
     * 删除套餐同时需要删除套餐和菜品的关联数据
     * @param ids
     */
    @Transactional
    @Override
    public void removeWithDish(List<Long> ids) {
            //查询套餐的状态，确定是否可以删除
            //select count(*) from setmeal where id in {1,2,3} and status=1;
            LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper();
            queryWrapper.in(Setmeal::getId,ids);
            queryWrapper.eq(Setmeal::getStatus,1);
            int count=this.count(queryWrapper);
            if(count>0){
                //如果不能删除，跑出一个业务异常
                throw new CustomerException("套餐正在售卖中，不能删除");
            }
            //如果可以删除，先删除套餐中的数据-setmeal
            this.removeByIds(ids);
            //删除关系表中的数据-setmeal_dish
            //delete from setmeal_dish wherer setmeal_id in {1,2,3};
            LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper=new LambdaQueryWrapper<>();
            lambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
            //删除关系表中的数据-setmeal_dish
            setmealDishService.remove(lambdaQueryWrapper);
    }
}
