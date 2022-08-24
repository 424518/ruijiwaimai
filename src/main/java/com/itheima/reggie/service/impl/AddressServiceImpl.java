package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.entity.AddressBook;
import com.itheima.reggie.mapper.AddressBookMapper;
import com.itheima.reggie.service.AddressBookService;

import org.springframework.stereotype.Service;

/**
 * @author shkstart
 * @create 2022-08-21 16:16
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressBookMapper,AddressBook> implements AddressBookService{
}
