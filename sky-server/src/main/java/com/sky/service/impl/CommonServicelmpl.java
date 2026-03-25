package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.mapper.CommonMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CommonService;
import com.sky.vo.DishVO;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommonServicelmpl implements CommonService {
    @Autowired
    private CommonMapper commonMapper;
    @Override
    @RequestBody
    public void save( DishDTO dishDTO) {
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
       commonMapper.insert(dish);


    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result delete(List<Long> ids) {
//        Dish dish=new Dish();
//        BeanUtils.copyProperties(dishDTO,dish);

        for (Long id : ids) {
            commonMapper.delete(id);
        }

        return Result.success();
    }
    @Override
    public List<Dish> list(Long categoryId) {
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return commonMapper.list(dish);
    }
    @Override
    public DishVO select(Long id) {
        DishVO dishVO=commonMapper.select(id);
        return dishVO;
    }

    @Override
    public PageResult pageQuery(DishPageQueryDTO queryDTO) {
        // 1. 设置分页参数
        PageHelper.startPage(queryDTO.getPage(), queryDTO.getPageSize());

        // 2. 执行查询 (需要在 Mapper 中写对应的 XML 或注解)
        // 注意：这里返回的应该是 DishVO 或者 Dish 列表，通常为了展示会包装成 VO
        Page<Dish> page = commonMapper.pageQuery(queryDTO);

        // 3. 获取总记录数
        long total = page.getTotal();

        // 4. 获取当前页数据列表
        List<Dish> records = page.getResult();

        // 5. 封装结果返回 (假设你有 PageResult 类)
        return new PageResult(total, records);
    }

    @Override
    public Integer startorstop(Integer status,Long id) {
        return commonMapper.startorstop(status,id);

    }

    @Override
    public void update(DishDTO dishDTO) {
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        commonMapper.update(dish);
    }
}
