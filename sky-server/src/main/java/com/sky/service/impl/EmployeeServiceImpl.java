package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(@NonNullDecl EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // TODO 后期需要进行md5加密，然后再进行比对
      password= DigestUtils.md5Hex(password.getBytes()).toUpperCase();;
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    @Override
    @RequestBody
    public void save(EmployeeDTO employeeDTO) {
        Employee employee =new Employee();
//        employee.setId(employee.getId());
        BeanUtils.copyProperties(employeeDTO,employee);
        //在这里设置账号的禁用状态 1表示启用 0 表示禁用
        employee.setStatus(StatusConstant.ENABLE);
        //m默认密码，进行md5加密，不能直接开始设置
        employee.setPassword(DigestUtils.md5Hex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        //设置当前的创建时间和修改时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        //设置创建人和修改人
        //TODO后期改为用户的id
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.insert(employee);
    }
//   分页查询
    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO){
//        SELECT *
//FROM Employee
//ORDER BY id                -- ⚠️ 必须包含 ORDER BY 子句
//OFFSET 0 ROWS              -- 跳过前 0 行（从第1条开始）
//FETCH NEXT 10 ROWS ONLY;   -- 只取接下来的 10 行
//        因为下载了pagehelper这个分页查询插件
        PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize()) ;
        Page<Employee>page=employeeMapper.pageQuery(employeePageQueryDTO);
        long total=page.getTotal();
        List<Employee>records=page.getResult();
        return  new PageResult(total,records);
    }

    @Override
    public void startorstop(Integer status, long id) {

//        Employee employee=new Employee();
//        employee.setStatus(status);
//        employee.setId(id);
        Employee employee=Employee.builder()
                        .status(status).id(id).build();
//        这两个种变成效果都一样，只不过现在的主流是链式编程

        employeeMapper.update(employee);//因为想要在这传入一个动态参数，所以要传入一个实体类

    }
}
