package com.sky.mapper;

import com.sky.annotation.AtuoFill;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {
    @Insert("insert into setmeal_dish (setmeal_id, dish_id, name, price, copies) " +
            "values (#{setmealId},#{dishId},#{name},#{price},#{copies})")
    @AtuoFill(OperationType.INSERT)
    void save(Setmeal setmeal, SetmealDish setmealDish);
    @Select("select *from dish where id=#{id}")
    DishVO select(Long id);

    List<Dish> list(Dish dish);
}
