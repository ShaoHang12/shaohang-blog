package com.xsh.blog.business.user.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xsh.blog.business.user.dto.LoginDto;
import com.xsh.blog.business.user.dto.RegisterDto;
import com.xsh.blog.business.user.entity.User;
import com.xsh.blog.business.user.service.IUserService;
import com.xsh.blog.common.bean.ApiCode;
import com.xsh.blog.common.bean.ApiResult;
import com.xsh.blog.common.bean.BasePage;
import com.xsh.blog.common.utils.JwtUtil;
import com.xsh.blog.common.utils.LocalUser;
import com.xsh.blog.common.utils.RedisUtils;
import com.xsh.blog.config.annotation.authority.HasLogin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 *
 */
@Api(tags = "基础-用户接口")
@RestController
@RequestMapping("/business/user")
public class UserController {




    private IUserService userService;
    private RedisUtils redisUtils;
    @Autowired
    public UserController(IUserService userService, RedisUtils redisUtils) {
        this.userService = userService;
        this.redisUtils = redisUtils;
    }


    @ApiOperation(value = "获取用户列表")
    @GetMapping("/list")
    public ApiResult list(User param){
        BasePage<User> page = userService.page(param);
        List<User> records = page.getRecords();
        for (User record : records) {
            record.setPassword("******");
        }
        return ApiResult.ok(page);
    }


    @ApiOperation(value = "获取详细信息")
    @GetMapping(value = "/{id}")
    public ApiResult getInfo(@PathVariable("id") String id)
    {
        User user = userService.getById(id);
        return ApiResult.ok(user);
    }

    @ApiOperation(value = "新增")
    @PostMapping
    public ApiResult add(@RequestBody User param)
    {
        return ApiResult.result(userService.save(param));
    }

    @HasLogin
    @ApiOperation(value = "更新用户信息")
    @PutMapping
    public ApiResult edit(@RequestBody User param)
    {
        User user = LocalUser.getUser();
        if (!user.getRole().equals("admin")){
            if (!user.getUserId().equals(param.getUserId())){
                return ApiResult.fail("您无权修改此账号");
            }
            // 操作者只能更新自己
            param.setUserId(user.getUserId());
            boolean flag = userService.updateById(param);
            if (flag){
                // 同步更新redis 存储信息
                User newUser = userService.getById(user.getUserId());
                redisUtils.setObject(LocalUser.getToken(),newUser);
            }
            return ApiResult.result(flag);
        }else{
            // 管理员可以修改任意账号
            if (param.getUserId() == null){
                param.setUserId(user.getUserId());
            }else{
                param.setUserId(param.getUserId());
            }
            boolean flag = userService.updateById(param);
            if (flag){
                // 同步更新redis 存储信息
                User newUser = userService.getById(user.getUserId());
                redisUtils.setObject(LocalUser.getToken(),newUser);
            }
            return ApiResult.result(flag);
        }

    }


    @ApiOperation(value = "注册")
    @PostMapping("/register")
    public ApiResult register(@Valid @RequestBody RegisterDto param)
    {
        User one = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, param.getUsername()));
        if (one !=null){
            return ApiResult.fail("当前用户名已被注册,请换一个新用户名吧");
        }

        return ApiResult.result(userService.save(param.toUser()));
    }


    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public ApiResult login(@Valid @RequestBody LoginDto param)
    {
        JSONObject jsonObject = new JSONObject();
        User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, param.getUsername()).eq(User::getPassword, param.getPassword()));
        if (Objects.isNull(user)){
            return ApiResult.fail(ApiCode.LOGIN_EXCEPTION);
        }
        String tempToken = JwtUtil.createTempToken(user.getUsername());
        redisUtils.setObject(tempToken,user,24, TimeUnit.MINUTES);
        jsonObject.put("token",tempToken);
        jsonObject.put("role",user.getRole());
        return ApiResult.ok(jsonObject);
    }


    @ApiOperation(value = "获取用户信息")
    @GetMapping("/getUserInfo")
    private ApiResult getUserInfo(){
        User user = LocalUser.getUser();
        return ApiResult.ok(user);
    }

    @HasLogin
    @ApiOperation(value = "删除")
    @DeleteMapping("/{ids}")
    public ApiResult remove(@PathVariable String[] ids)
    {
        String role = LocalUser.getUser().getRole();
        if (role.equals("admin")){
            List<String> list = Arrays.asList(ids);
            return ApiResult.result(userService.removeByIds(list));
        }else{
            return ApiResult.fail("您无权删除用户");
        }

    }




}
