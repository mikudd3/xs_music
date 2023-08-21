package com.win.xs_music.controller;

import com.win.xs_music.common.CustomException;
import com.win.xs_music.common.R;
import com.win.xs_music.dto.AdminPageDto;
import com.win.xs_music.pojo.Admin;
import com.win.xs_music.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @project:
 * @author: mikudd3
 * @version: 1.0
 */

@RestController
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 管理员后台登录
     *
     * @param admin
     * @param request
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody Admin admin, HttpServletRequest request) {
        log.info("登录管理员后台所传入信息：{}", admin);
        try {
            return adminService.login(admin, request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    /**
     * 管理员界面员工管理分页查询
     *
     * @param adminPageDto
     * @return
     */
    @PostMapping("page")
    public R adminMgrPage(@RequestBody AdminPageDto adminPageDto) {
        log.info("管理员界面员工管理分页查询输入的信息为：{}", adminPageDto);
        try {
            return adminService.adminMgrPage(adminPageDto.getAdmin(), adminPageDto.getCurrentPage(), adminPageDto.getPageSize());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }


    /**
     * 添加员工
     *
     * @param admin
     * @return
     */
    @PostMapping("/add")
    public R add(@RequestBody Admin admin) {
        log.info("添加员工所输入信息：{}", admin);
        try {
            boolean save = adminService.save(admin);
            return save ? R.success("添加成功") : R.success("添加失败");
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    /**
     * 修改信息
     * @param admin
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody Admin admin) {
        log.info("修改员工所输入信息：{}", admin);
        try {
            boolean ret = adminService.updateById(admin);
            return ret ? R.success("修改成功") : R.success("修改失败");
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }

    }

    /**
     * 删除管理员
     * @param id
     * @return
     */
    @PostMapping("deleteById")
    public R deleteById(Integer id) {
        log.info("删除员工所输入信息：{}", id);
        try {
            boolean ret = adminService.removeById(id);
            return ret ? R.success("删除成功") : R.success("删除失败");
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }

    }
}
