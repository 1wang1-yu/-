package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AtuoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommonMapper {
    @Insert("insert into dish (name, category_id, price, image, description, status, create_time, update_time, create_user, update_user) "
            + "values (#{name},#{categoryId},#{price},#{image},#{description},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @AtuoFill(OperationType.INSERT)
    void insert(Dish dish);
    @Delete("delete from dish where id=#{id}")
    void delete(Long id);
    @Select("select *from  dish where id=#{id}")
    DishVO select(Long id);
    List<Dish> list(Dish dish);
    Page<Dish> pageQuery(DishPageQueryDTO queryDTO);
    @Update("update dish set status=#{status} where id=#{id} ")
    @AtuoFill(OperationType.UPDATE)
    Integer startorstop(@Param("status") Integer status,@Param("id") Long id);
    @AtuoFill(OperationType.UPDATE)
    void update(Dish dish);
}
