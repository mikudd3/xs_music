package com.win.xs_music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.win.xs_music.common.R;
import com.win.xs_music.mapper.UserMapper;
import com.win.xs_music.pojo.User;
import com.win.xs_music.service.UserService;
import com.win.xs_music.vo.UserCountVo;
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
    public R update(User user){
        QueryWrapper<User> query = Wrappers.query();
        query.eq("username",user.getUsername());
        User u = this.getOne(query);
        if (u!=null){
            if(u.getId() != user.getId()){
                return R.success("用户名以存在");
            }
        }
        boolean b = this.updateById(user);
        return b ? R.success("修改成功") : R.success("修改失败");
    }

    @Override
    public R selectUserCount() {
        //查找男用户数量
        QueryWrapper<User> men = Wrappers.query();
        men.eq("sex", 1);
        int menCount = this.count(men);
        //查找女用户数量
        QueryWrapper<User> women = Wrappers.query();
        women.eq("sex", 0);
        int womenCount = this.count(women);
        int userCount = menCount + womenCount;
        UserCountVo userCountVo = new UserCountVo(menCount, womenCount,userCount);
        return R.success(userCountVo);
    }


}
