package com.win.xs_music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.win.xs_music.common.R;
import com.win.xs_music.dto.UserLoginDto;
import com.win.xs_music.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface UserService extends IService<User> {

    /**
     * 用户分页查询
     *
     * @param currentPage
     * @param pageSize
     * @param user
     * @return
     */
    R getPage(Integer currentPage, Integer pageSize, User user);

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    R update(User user);

    /**
     * 查询用户数量
     *
     * @return
     */
    R selectUserCount();

    /**
     * 用户登录
     *
     * @param user
     * @param request
     * @return
     */
    R login(User user, HttpServletRequest request);

    /**
     * 获取当前登录的用户
     *
     * @return
     */
    R getUser();

    /**
     * 通过电话更新用户电话
     *
     * @param user
     * @return
     */
    R updatePhone(User user);

    /**
     * 发送短信
     *
     * @param phone
     * @param session
     * @return
     */
    R send(String phone, HttpSession session);

    /**
     * 短信登录
     *
     * @param userLoginDto
     * @param request
     * @return
     */
    R SMSLogin(UserLoginDto userLoginDto, HttpServletRequest request);

    /**
     * 获取短信验证码
     * @param phone
     * @param session
     * @return
     */
    R getLoginCode(String phone, HttpSession session);
}

