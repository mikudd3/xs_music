package com.win.xs_music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.win.xs_music.common.R;
import com.win.xs_music.pojo.Admin;

import javax.servlet.http.HttpServletRequest;

public interface AdminService extends IService<Admin> {

    //管理员界面员工管理分页查询
    R adminMgrPage(Admin admin, Integer currentPage, Integer pageSize);

    //管理员登录
    R login(Admin admin, HttpServletRequest request);

}
