package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AtuoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.*;

import java.util.List;

//用于实现sql语句
@Mapper
public interface CategoryMapper {

   @Insert("insert into category(type,name,sort,status,create_time,update_time,create_user,update_user)"+
           "values (#{type},#{name},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
   @AtuoFill(value = OperationType.INSERT)
    void  insert(Category category);
    @Select("select *from  category where type=#{type} ")
    List<Category> getType(Long type);
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);
//    @Update("update category set type=#{type},name=#{name},sort=#{sort},status=#{status}" +
//            ",create_user=#{createUser},update_user=#{updateUser},create_time=#{createTime},update_time=#{updateTime} where id=#{id}")
    @AtuoFill(value = OperationType.UPDATE)
    void update(Category category);
    @Delete("delete from category where id=#{id}")
    void delete(Long id);
}

