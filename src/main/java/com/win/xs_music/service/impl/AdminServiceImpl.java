package com.win.xs_music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.win.xs_music.common.CustomException;
import com.win.xs_music.common.R;
import com.win.xs_music.mapper.AdminMapper;
import com.win.xs_music.pojo.Admin;
import com.win.xs_music.service.AdminService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @project:
 * @author: mikudd3
 * @version: 1.0
 */

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {


    @Autowired
    private AdminMapper adminMapper;

    /**
     * 管理员界面员工管理分页查询
     *
     * @param admin
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public R adminMgrPage(Admin admin, Integer currentPage, Integer pageSize) {
        try {
            Page<Admin> page = new Page<>(currentPage, pageSize);
            //创建条件构造器
            LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
            //如果又条件
            if (admin != null) {
                wrapper.like(StringUtils.isNotEmpty(admin.getName()), Admin::getName, admin.getName())
                        .eq(admin.getZt() != null, Admin::getZt, admin.getZt());
            }
            //进行查询
            this.page(page, wrapper);
            return R.success(page);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    /**
     * 管理员登录
     *
     * @param admin
     * @param request
     * @return
     */
    @Override
    public R login(Admin admin, HttpServletRequest request) {
        try {
            LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Admin::getName, admin.getName());
            Admin a = getOne(wrapper);
            //进行查询
            //如果用户不存在
            if (a == null) {
                return R.error("用户不存在");
            }
            //如果密码错误
            if (!admin.getPassword().equals(a.getPassword())) {
                return R.error("密码错误");
            }
            //密码正确,将其放入当前线程变量中
            request.getSession().setAttribute("admin", a.getId());
            return R.success(a);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }


    }


}
