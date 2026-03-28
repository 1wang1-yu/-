package com.sky.aspect;

import com.sky.annotation.AtuoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AtuoFillAspect {

    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AtuoFill)")
    public void atuofillpointcut() {
    }

    @Before("atuofillpointcut()")
    public void atuofill(JoinPoint joinPoint) {
        log.info("开始进行公共字段的自动填充");

        // 1. 获取方法签名和注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AtuoFill atuoFill = signature.getMethod().getAnnotation(AtuoFill.class);
        OperationType operationType = atuoFill.value();

        // 2. 获取方法参数
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }

        // 3. 【关键修复】寻找实体对象参数
        // 并不是所有被拦截方法的第一个参数都是实体对象（例如 deleteById(Long id)）
        // 我们需要遍历参数，找到那个非基础类型、非包装类型的对象作为 entity
        Object entity = null;

        for (Object arg : args) {
            if (arg != null && !isSimpleType(arg.getClass())) {
                entity = arg;
                break; // 找到第一个复杂对象即认为是实体（通常 Mapper 方法只有一个实体参数）
            }
        }

        // 如果没有找到实体对象，直接返回，不进行填充
        if (entity == null) {
            log.debug("未找到实体对象参数，跳过自动填充");
            return;
        }

        // 4. 准备数据
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        // 5. 根据操作类型进行反射赋值
        try {
            if (operationType == OperationType.INSERT) {
                // 插入时填充 4 个字段
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                setCreateTime.invoke(entity, now);
                setCreateUser.invoke(entity, currentId);
                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);

            } else if (operationType == OperationType.UPDATE) {
                // 更新时填充 2 个字段
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);
            }
        } catch (Exception e) {
            log.error("自动填充失败", e);
            // 可以选择抛出运行时异常，或者根据业务需求决定如何处理
            throw new RuntimeException("自动填充公共字段失败", e);
        }
    }

    /**
     * 判断是否是简单类型 (基础类型、包装类型、String、Date/Time等)
     * 如果是简单类型，则不是我们要找的实体对象
     */
    private boolean isSimpleType(Class<?> clazz) {
        return clazz.isPrimitive() ||
                clazz.getName().startsWith("java.lang.") ||
                clazz.getName().startsWith("java.time.") ||
                clazz.getName().startsWith("java.util.Date");
    }
}