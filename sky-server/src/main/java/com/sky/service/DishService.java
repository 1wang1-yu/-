package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.entity.Dish;
import com.sky.entity.SetmealDish;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
     void save( SetmealDish setmealDish);
     DishVO getid(Long id);
     List<Dish> list(Long categoryId);
}
