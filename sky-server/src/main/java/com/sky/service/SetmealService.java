package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;
import org.springframework.stereotype.Service;


public interface SetmealService {
    void save(SetmealDTO setmealDTO);

    SetmealVO getid(Long id);

    PageResult quert(SetmealPageQueryDTO setmealPageQueryDTO);

    void update(SetmealDTO setmealDTO);

    void delete(Long id);

    void startorstop(Long status,Long id);
}
