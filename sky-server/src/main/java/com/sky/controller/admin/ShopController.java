package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/shop")
@RestController
@Api(tags = "店铺相关接口")
@Slf4j
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;

    @PutMapping("/{status}")
    @ApiOperation("设置店铺的营业状态")
    public Result setStatus(@PathVariable Integer status) {
        log.info("店铺的营业状态为:{}", status == 1 ? "营业中" : "不营业");
        redisTemplate.opsForValue().set("shop_status", status);
        return Result.success();
    }

    @GetMapping("/status")
    @ApiOperation("获取店铺的营业状态")
    public Result<Integer> getStatus() {
        // 1. 获取数据 (可能是 null)
        Integer status = (Integer) redisTemplate.opsForValue().get("shop_status");

        // 2. 【关键修复】如果 Redis 里没有数据，默认为 0 (不营业)
        // 这避免了后面 status == 1 时发生空指针异常 (自动拆箱失败)
        if (status == null) {
            status = 0;
        }

        // 3. 打印日志 (此时 status 肯定不是 null 了，安全)
        log.info("店铺的营业状态为:{}", status == 1 ? "营业中" : "不营业");

        // 4. 返回结果
        return Result.success(status);
    }
}