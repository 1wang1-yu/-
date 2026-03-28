package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealSevicelmpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    public void save(SetmealDTO setmealDTO) {
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmeal.setStatus(StatusConstant.DISABLE);
        setmealMapper.save(setmeal);
        Long setmealId=setmeal.getId();
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealId);
            // 现在每个 setmealDish 都知道自己属于套餐 101 了
        });
        setmealDishMapper.insertBatch(setmealDishes);
    }

    @Override
    public SetmealVO getid(Long id) {
        // 1. 查询数据库得到 Entity
        Setmeal setmeal = setmealMapper.getid(id);

        // 2. 转换为 VO (如果需要查询分类名称等额外信息，可以在这里补充)
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);

        return setmealVO;

    }

    @Override
    public PageResult quert(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<Setmeal> page=setmealMapper.quert(setmealPageQueryDTO);
       return new PageResult(page.getTotal(),page.getResult());

    }

    @Override
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.update(setmeal);
        Long setmealId=setmeal.getId();
        setmealDishMapper.deleteBySetmealId(setmealId);

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealId);
            // 现在每个 setmealDish 都知道自己属于套餐 101 了
        });
        setmealDishMapper.insertBatch(setmealDishes);
    }

    @Override
    public void delete(Long ids) {
        Setmeal setmeal=new Setmeal();

        setmealDishMapper.deleteBySetmealId(ids);
        setmealMapper.delete(ids);
    }

    @Override
    public void startorstop(Long status,Long id) {
        setmealMapper.startorstop(status,id);
    }
}
