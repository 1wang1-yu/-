package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AtuoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.result.PageResult;
import org.apache.ibatis.annotations.*;

@Mapper
public interface SetmealMapper {
//    @AtuoFill(OperationType.INSERT)
//    void save(Setmeal setmeal);
    @Select("select *from setmeal where id=#{id}")
    Setmeal getid(Long id);
    @AtuoFill(OperationType.INSERT)
    void save(Setmeal setmeal);


    Page<Setmeal> quert(SetmealPageQueryDTO setmealPageQueryDTO);
    @Update("update setmeal set category_id=#{categoryId},name=#{name},price=#{price},image=#{image},description=#{description},status=#{status} where id=#{id}")
    @AtuoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);
    @Delete("delete from setmeal where id=#{ids}")
    void delete(Long ids);
    @Update("update setmeal set status=#{status} where id=#{id}")
    void startorstop(Long status,Long id);
}
