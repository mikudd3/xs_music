package com.win.xs_music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.win.xs_music.common.R;
import com.win.xs_music.mapper.UserMapper;
import com.win.xs_music.pojo.User;
import com.win.xs_music.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Override
    public R getPage(Integer currentPage, Integer pageSize, User user) {
        Page<User> page = new Page(currentPage,pageSize);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        //进行动态sql
        if (user != null){
            wrapper.like(StringUtils.isNotEmpty(user.getUsername()),User::getUsername,user.getUsername())
                    .eq(user.getZt() != null,User::getZt,user.getZt());
        }
        //进行分页查询
        this.page(page,wrapper);
        return R.success(page);
    }
}
