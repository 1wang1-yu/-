package com.sky.controller.admin;

import com.sky.properties.JwtProperties;
import com.sky.service.DishService;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@Slf4j
@Api(tags = "菜品相关接口")//对类的一个描述
public class DishController {
    @Autowired
    private AliOssUtil aliOssUtil;
    @Autowired
    private  DishService dishService;
    @Autowired
    private JwtProperties jwtProperties;

//    public Result<DishVO> getid(@PathVariable Long id){
//        DishVO dishVO=new DishVO();
//        dishVO.setId(id);
//        dishService.getid(id);
//        return Result.success(dishVO);
//    }
////    @GetMapping("/list")
////    @ApiOperation("根据分类id查询菜品")
////    public Result<List<Dish>> list(Long categoryId){
////        List<Dish> list = dishService.list(categoryId);
////        return Result.success(list);
////    }
//    @PostMapping
//    @ApiOperation("新增菜品的相关接口")
//    public Result save(@RequestBody SetmealDish setmealDish){
//        dishService.save(setmealDish);
//        return  Result.success();
//    }

}
