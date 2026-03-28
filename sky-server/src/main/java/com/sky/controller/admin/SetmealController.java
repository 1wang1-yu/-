package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@Api(tags = "套餐相关接口")//对类的一个描述
public class SetmealController {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealService setmealService;
    @PostMapping
    @ApiOperation("新增套餐")
    public Result save(@RequestBody SetmealDTO setmealDTO){
        setmealService.save(setmealDTO);
        return Result.success();
    }
    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetmealVO> getid(@PathVariable Long id){
        SetmealVO vo = setmealService.getid(id);
        return Result.success(vo);

    }
    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> query(SetmealPageQueryDTO setmealPageQueryDTO){

        return Result.success(setmealService.quert(setmealPageQueryDTO));


    }
    @PutMapping
    @ApiOperation("修改套餐")
    public Result update(@RequestBody SetmealDTO setmealDTO){
        setmealService.update(setmealDTO);
        return Result.success();


    }
    @DeleteMapping
    @ApiOperation("删除套餐")
    public Result delete(@RequestParam Long ids){
        setmealService.delete(ids);
        return Result.success();
    }
    @PostMapping("/status/{status}")
    public Result startorstop(@PathVariable Long status,@RequestParam Long id){
        setmealService.startorstop(status,id);
        return Result.success();
    }

}
