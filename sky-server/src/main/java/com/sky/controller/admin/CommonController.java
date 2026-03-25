package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CommonService;
import com.sky.utils.AliOssUtil;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import io.swagger.v3.oas.models.links.Link;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

//通用接口
@RestController
@RequestMapping("/admin")
@Slf4j
@Api(tags = "通用接口")
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;
    @Autowired
    private CommonService commonService;

    @PostMapping("/common/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file){
        log.info("文件上传：{}",file);
        try {
            //原始文件名
            String orignalFilename=file.getOriginalFilename();
            //截取原始文件名的后缀 png jpg
            String extension = orignalFilename.substring(orignalFilename.lastIndexOf("."));
//           构建新文件名称
            String objectname = UUID.randomUUID().toString() + extension;
//            path代表文件的请求路径
            String filePath=aliOssUtil.upload(file.getBytes(),objectname);
            return Result.success(filePath);
        } catch (IOException e) {
            log.info("文件上传失败{}",e);
            throw new RuntimeException(e);

        }


    }
    @GetMapping("/dish/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> select(@PathVariable Long id){
        log.info("查询菜品{}",id);
        DishVO dishVO =new DishVO();
        dishVO.setId(id);
       dishVO= commonService.select(id);
        return Result.success(dishVO);
    }

//    public Result<Dish> selectid(Integer categoryId){
//        log.info("根据分类查询菜品：categoryid",categoryId);
//        Dish dish=new Dish();
//        dish=dishDTO.setCategoryId();
//
//    }
@GetMapping("/dish/list")
@ApiOperation("根据分类id查询菜品")
public Result<List<Dish>> list(Long categoryId){
    List<Dish> list = commonService.list(categoryId);
    return Result.success(list);
}

    @GetMapping("/dish/page")
    @ApiOperation("菜品分页查询")
    public  Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){
        log.info("菜品分页查询：{}",dishPageQueryDTO);
        PageResult pageResult=commonService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }
    @PostMapping("/dish")
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("新增菜品：{}",dishDTO);
       commonService.save(dishDTO);
        return Result.success();
    }
    @DeleteMapping("/dish")
    @ApiOperation("删除菜品")

    public Result delete(@RequestParam List<Long> ids){
        log.info("删除菜品：{}",ids);
        commonService.delete(ids);
        return Result.success();
    }
    @PostMapping("/dish/status/{status}")
    @ApiOperation("菜品的起售禁售")
    public Result startorstop(@PathVariable Integer status, @RequestParam Long id){
        log.info("菜品是否起售：{}",status);
        commonService.startorstop(status,id);
        return Result.success();
    }
    @PutMapping("/dish")
    public Result update(@RequestBody  DishDTO dishDTO){
        commonService.update(dishDTO);
        return Result.success();
    }

}
