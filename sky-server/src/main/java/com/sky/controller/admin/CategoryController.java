package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类相关接口")//对类的一个描述
public class CategoryController {
    @Autowired
    private CategoryService categoryeeService;
    @Autowired
    private JwtProperties jwtProperties;
//    新增分类
    @PostMapping
    @ApiOperation("新增分类")
    public Result save(@RequestBody CategoryDTO categoryDTO){
        log.info("新增分类：{}",categoryDTO);
        categoryeeService.save(categoryDTO);

        return Result.success();
    }
    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> getType(@RequestParam Long type) {
        log.info("查询类型为{}的分类", type);
        List<Category> list = categoryeeService.getType(type);
        return Result.success(list); // 此时泛型匹配，不再报错
    }
    // 分页查询接口（保留）
    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分类分页查询：{}", categoryPageQueryDTO);
        PageResult pageResult = categoryeeService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }
    @PutMapping
    @ApiOperation("修改类")
    public Result update(@RequestBody CategoryDTO categoryDTO){
        log.info("修改分类：{}",categoryDTO);
        categoryeeService.update(categoryDTO);
        return Result.success();
    }
    @DeleteMapping
    @ApiOperation("删除分类")
    public Result delete( Long id){
        log.info("删除分类：{}",id);
        categoryeeService.delete(id);
        return Result.success();
    }
    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用分类类型")
    public  Result  startorstop(@PathVariable("status") Integer status ,long id){
        log.info("启动禁用员工账号{}，{}",status,id);
        categoryeeService.startorstop(status,id);
        return  Result.success();
    }


}
