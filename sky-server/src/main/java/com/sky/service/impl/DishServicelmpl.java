package com.sky.service.impl;

import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServicelmpl implements DishService {
    @Autowired
    private DishMapper dishMapper;

    @Override
    public void save( SetmealDish setmealDish) {
        Setmeal setmeal =new Setmeal();
        BeanUtils.copyProperties(setmealDish,setmeal);
        setmeal.setStatus(StatusConstant.DISABLE);
        //因为要求新增的套餐默认为停售的状态
        dishMapper.save(setmeal,setmealDish);

    }
    @Override
    public List<Dish> list(Long categoryId) {
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return dishMapper.list(dish);
    }

    @Override
    public DishVO getid(Long id) {
        dishMapper.select(id);
        return null;
    }
}
