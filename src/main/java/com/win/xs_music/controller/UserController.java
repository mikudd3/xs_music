package com.win.xs_music.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.win.xs_music.common.CustomException;
import com.win.xs_music.common.R;
import com.win.xs_music.dto.UserLoginDto;
import com.win.xs_music.dto.UserPageDto;
import com.win.xs_music.mapper.UserMapper;
import com.win.xs_music.pojo.User;
import com.win.xs_music.service.UserService;
import com.win.xs_music.vo.UserCountVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    //管理员分页查询用户
    @PostMapping("/page")
    public R selectUsers(@RequestBody UserPageDto userPageDto) {
        log.info("管理员界面用户分页查询输入的信息为：{}", userPageDto);
        try {
            return userService.getPage(userPageDto.getCurrentPage(), userPageDto.getPageSize(), userPageDto.getUser());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    //管理员修改用户信息
    @PostMapping("/update")
    public R update(@RequestBody User user) {
        log.info("修改用户信息:{}", user);
        try {
            return userService.update(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    @PostMapping("/deleteById")
    public R delete(Integer id) {
        log.info("要删除的用户id为:{}", id);
        boolean b = false;
        try {
            b = userService.removeById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
        return b ? R.success("删除成功") : R.success("删除失败");
    }

    @PostMapping("/getUserCount")
    public R selectUserCount() {
        log.info("查询用户个数");
        try {
            return userService.selectUserCount();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }


    //密码登录
    @PostMapping("login")
    public R login(@RequestBody User user, HttpServletRequest request) {
        log.info("输入的信息为:{}", user);
        try {
            return userService.login(user, request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }


    //验证码登录
    @PostMapping("login1")
    public R login1(@RequestBody UserLoginDto userLoginDto, HttpServletRequest request) {
        log.info("输入的信息为:{}", userLoginDto);
        try {
            return userService.login1(userLoginDto, request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }


    //获取当前登录的用户
    @GetMapping("/getUser")
    public R getUser() {
        try {
            return userService.getUser();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }


    //发送验证码
    @PostMapping("/send")
    public R send(@RequestBody User user, HttpSession session) {
        log.info("输入的手机号为：{}", user.getPhone());
//        return null;
        try {
            return userService.send(user.getPhone(), session);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    @PostMapping("/getLoginCode")
    public R getLoginCode(@RequestBody User user, HttpSession session) {
        log.info("获取的用户信息为：{}", user);
        try {
            return userService.getLoginCode(user.getPhone(), session);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }

}
