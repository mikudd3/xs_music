package com.win.xs_music.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.win.xs_music.common.R;
import com.win.xs_music.dto.UserPageDto;
import com.win.xs_music.mapper.UserMapper;
import com.win.xs_music.pojo.User;
import com.win.xs_music.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
         return userService.getPage(userPageDto.getCurrentPage(), userPageDto.getPageSize(), userPageDto.getUser());
    }

    //管理员修改用户信息
    @PostMapping("/update")
    public R update(@RequestBody User user) {
        log.info("修改用户信息:{}", user);
        //先判断要修改的用户名是否存在
        QueryWrapper<User> query = Wrappers.query();
        query.eq("username",user.getUsername());
        User u = userService.getOne(query);



        if (u!=null){
            if(u.getId() != user.getId()){
                return R.success("用户名以存在");
            }
        }
        boolean b = userService.updateById(user);
        return b ? R.success("修改成功") : R.success("修改失败");
    }

    @PostMapping("/deleteById")
    public R delete(Integer id){
        log.info("要删除的用户id为:{}",id);
        boolean b = userService.removeById(id);
        return b ? R.success("删除成功") : R.success("删除失败");
    }

}
