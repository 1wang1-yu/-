package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.vo.DishVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommonService {

     void save(DishDTO dishDTO) ;

    // void delete(Long id);
    List<Dish> list(Long categoryId);
     @Transactional(rollbackFor = Exception.class)
     Result delete(List<Long> ids);

     DishVO select(Long id);

     PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

     Integer startorstop(Integer status,Long id);

     void update(DishDTO dishDTO);
}
