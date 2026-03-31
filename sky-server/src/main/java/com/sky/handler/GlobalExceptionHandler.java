package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Duplication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){//全局的异常处理器去处理这个异常
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }
//    处理sql异常
    @ExceptionHandler(com.microsoft.sqlserver.jdbc.SQLServerException.class)
    public Result exceptionHandler(com.microsoft.sqlserver.jdbc.SQLServerException ex){
        String message =ex.getMessage();
        if (message.contains("Duplicate entry")||message.contains("唯一索引")||message.contains("重复键")){
            String[] split=message.split("");
            String  username=split[2];
            String msg =username+ MessageConstant.ALREADY_EXISTS;
            return  Result.error(msg);
        }
        else {
            return  Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }

}
