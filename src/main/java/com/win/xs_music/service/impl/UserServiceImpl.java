package com.win.xs_music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.win.xs_music.common.BaseContext;
import com.win.xs_music.common.R;
import com.win.xs_music.mapper.UserMapper;
import com.win.xs_music.pojo.User;
import com.win.xs_music.service.UserService;
import com.win.xs_music.util.SMSUtils;
import com.win.xs_music.util.ValidateCodeUtils;
import com.win.xs_music.vo.UserCountVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.file.NotLinkException;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Override
    public R getPage(Integer currentPage, Integer pageSize, User user) {
        Page<User> page = new Page(currentPage, pageSize);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        //进行动态sql
        if (user != null) {
            wrapper.like(StringUtils.isNotEmpty(user.getUsername()), User::getUsername, user.getUsername())
                    .eq(user.getZt() != null, User::getZt, user.getZt());
        }
        //进行分页查询
        this.page(page, wrapper);
        return R.success(page);
    }

    public R update(User user) {
        QueryWrapper<User> query = Wrappers.query();
        query.eq("username", user.getUsername());
        User u = this.getOne(query);
        if (u != null) {
            if (u.getId() != user.getId()) {
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
        UserCountVo userCountVo = new UserCountVo(menCount, womenCount, userCount);
        return R.success(userCountVo);
    }

    @Override
    public R login(User user, HttpServletRequest request) {
        QueryWrapper<User> query = Wrappers.query();
        query.eq("phone", user.getPhone());
        query.eq("password", user.getPassword());
        User one = this.getOne(query);
        if (one == null) {
            return R.error("账号或密码错误");
        }
        request.getSession().setAttribute("user", one.getId());
        return R.success(one);

    }

    //获取当前登录的用户
    @Override
    public R getUser() {
        //从本地线程获取登录用户的id
        Integer userId = BaseContext.getCurrentId();
        //根据id查询用户信息
        User u = this.getById(userId);
        //根据用户id
        return R.success(u);
    }

    @Override
    public R updatePhone(User user) {
        //从本地线程获取当前登录用户的id
        Integer id = BaseContext.getCurrentId();
        //创建user对象
        user.setId(id);
        //更新电话
        boolean ret = this.updateById(user);
        return ret ? R.success("更新成功") : R.error("更新失败");
    }

    @Override
    public R send(String phone, HttpSession session) {
        if (StringUtils.isNotEmpty(phone)) {
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("生成的验证码为：{}", code);
            SMSUtils.sendMessage("苏健昌的音乐", "SMS_462595788", phone, code);
            session.setAttribute("code", code);
            return R.success("短信发送成功");
        }
        return R.error("登陆失败");
    }


}
