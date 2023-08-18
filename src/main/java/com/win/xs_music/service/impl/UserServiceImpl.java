package com.win.xs_music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.win.xs_music.common.BaseContext;
import com.win.xs_music.common.CustomException;
import com.win.xs_music.common.R;
import com.win.xs_music.dto.UserLoginDto;
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
        Page<User> page = null;
        try {
            page = new Page(currentPage, pageSize);
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            //进行动态sql
            if (user != null) {
                wrapper.like(StringUtils.isNotEmpty(user.getUsername()), User::getUsername, user.getUsername())
                        .eq(user.getZt() != null, User::getZt, user.getZt());
            }
            //进行分页查询
            this.page(page, wrapper);
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
        return R.success(page);
    }

    public R update(User user) {
        boolean b = false;
        try {
            QueryWrapper<User> query = Wrappers.query();
            query.eq("username", user.getUsername());
            User u = this.getOne(query);
            if (u != null) {
                if (u.getId() != user.getId()) {
                    return R.success("用户名以存在");
                }
            }
            b = this.updateById(user);
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
        return b ? R.success("修改成功") : R.success("修改失败");
    }

    @Override
    public R selectUserCount() {
        UserCountVo userCountVo = null;
        try {
            //查找男用户数量
            QueryWrapper<User> men = Wrappers.query();
            men.eq("sex", 1);
            int menCount = this.count(men);
            //查找女用户数量
            QueryWrapper<User> women = Wrappers.query();
            women.eq("sex", 0);
            int womenCount = this.count(women);
            int userCount = menCount + womenCount;
            userCountVo = new UserCountVo(menCount, womenCount, userCount);
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
        return R.success(userCountVo);
    }

    @Override
    public R login(User user, HttpServletRequest request) {
        User one = null;
        try {
            QueryWrapper<User> query = Wrappers.query();
            query.eq("phone", user.getPhone());
            query.eq("password", user.getPassword());
            one = this.getOne(query);
            if (one == null) {
                return R.error("账号或密码错误");
            }
            request.getSession().setAttribute("user", one.getId());
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
        return R.success(one);

    }

    //获取当前登录的用户
    @Override
    public R getUser() {
        //从本地线程获取登录用户的id
        Integer userId = BaseContext.getCurrentId();
        //根据id查询用户信息
//        User u = this.getById(64); //测试
        User u = null;
        try {
            u = this.getById(userId);
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
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
        boolean ret = false;
        try {
            ret = this.updateById(user);
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
        return ret ? R.success("更新成功") : R.error("更新失败");
    }

    @Override
    public R send(String phone, HttpSession session) {
        if (StringUtils.isNotEmpty(phone)) {
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("生成的验证码为：{}", code);
            //SMSUtils.sendMessage("苏健昌的音乐", "SMS_462595788", phone, code);
            session.setAttribute(phone, code);
            return R.success("短信发送成功");
        }
        return R.error("登陆失败");
    }

    @Override
    public R login1(UserLoginDto userLoginDto, HttpServletRequest request) {
        String codeSession = (String) request.getSession().getAttribute(userLoginDto.getPhone());
//        log.info(codeSession);
//        System.out.println(codeSession != null);
        String code = userLoginDto.getCode() + "";
//        log.info(code);
        if (codeSession != null && codeSession.equals(code)) {
//            log.info("22222");
            QueryWrapper<User> query = Wrappers.query();
            query.eq("phone", userLoginDto.getPhone());
            User user = this.getOne(query);
            if (user == null) {
//                log.info("3333");
                user = new User();
                user.setPhone(userLoginDto.getPhone());
                user.setUsername(userLoginDto.getPhone());
                user.setPassword(userLoginDto.getPhone());
                this.save(user);
            }
            //查找新创建用户的id
            user = this.getOne(query);
            request.setAttribute("user", user.getId());
            return R.success(user);
        }
        return R.error("登录失败");
    }


    @Override
    public R getLoginCode(String phone, HttpSession session) {
        Object code = session.getAttribute(phone);
        log.info("验证码为：{}", code);
        return R.success(code);
    }
}
