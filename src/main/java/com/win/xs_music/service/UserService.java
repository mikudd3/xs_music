package com.win.xs_music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.win.xs_music.common.R;
import com.win.xs_music.dto.UserLoginDto;
import com.win.xs_music.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface UserService extends IService<User> {

    //
    R getPage(Integer currentPage, Integer pageSize, User user);

    R update(User user);

    R selectUserCount();

    R login(User user, HttpServletRequest request);

    //获取当前登录的用户
    R getUser();

    R updatePhone(User user);

    R send(String phone, HttpSession session);


    R login1(UserLoginDto userLoginDto, HttpServletRequest request);

    R getLoginCode(String phone, HttpSession session);
}

