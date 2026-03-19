package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")//对类的一个描述
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "员工登入")//对方法进行描述
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
         Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value = "员工退出")//对方法进行描述
    public Result<String> logout() {
        return Result.success();
    }

    @PostMapping
    @ApiOperation("新增员工")
    public  Result save(@RequestBody EmployeeDTO employeeDTO){
        log.info("新增员工：{}",employeeDTO);
        System.out.println("当前线程的id:"+Thread.currentThread().getId());
        employeeService.save(employeeDTO);

        return Result.success();
    }
//    对于查询还是要把泛型给写上
    @GetMapping("/page")
    @ApiOperation("员工分页查询")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("员工分页查询：{}",employeePageQueryDTO);
      PageResult pageResult= employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult) ;
    }
//为什么要加这个pathvariable这个注解因为 status是一个路径参数
    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用员工账号")
    public  Result  startorstop(@PathVariable("status") Integer status ,long id){
        log.info("启动禁用员工账号{}，{}",status,id);
        employeeService.startorstop(status,id);
        return  Result.success();
    }
 @ GetMapping("/{id}")
@ApiOperation("根据id查询员工信息")
    public Result<Employee> getbyId(@PathVariable  Long id){
        Employee employee=employeeService.getbyId(id);

        return Result.success(employee);
    }
//    在这里这所以不使用postmapping是因为他与新增员工信息的路径相同请求方式也相同，路径不知道该访问哪一个就报错了
    @PutMapping
    @ApiOperation("编辑员工信息")
public  Result  update(@RequestBody  EmployeeDTO employeeDTO){
        log.info("编辑员工信息 {}",employeeDTO);
        employeeService.update(employeeDTO);
        return  Result.success();
}
}
