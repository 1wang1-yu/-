package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {
//     Category getType(Long type);
    List<Category> getType(Long type);
    //新增员工
     void save(CategoryDTO categoryDTO);
     PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void update(CategoryDTO categoryDTO);



    void delete(Long id);

    void startorstop(Integer status, long id);
}
