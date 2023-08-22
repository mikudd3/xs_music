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

    /**
     * 用户分类查询
     *
     * @param currentPage
     * @param pageSize
     * @param user
     * @return
     */
    @Override
    public R getPage(Integer currentPage, Integer pageSize, User user) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }

    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    public R update(User user) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    /**
     * 获取用户数量
     *
     * @return
     */
    @Override
    public R selectUserCount() {
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
            UserCountVo userCountVo = new UserCountVo(menCount, womenCount, userCount);
            return R.success(userCountVo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    /**
     * 密码登录
     *
     * @param user
     * @param request
     * @return
     */
    @Override
    public R login(User user, HttpServletRequest request) {
        try {
            QueryWrapper<User> query = Wrappers.query();
            query.eq("phone", user.getPhone());
            query.eq("password", user.getPassword());
            User one = this.getOne(query);
            if (one == null) {
                return R.error("账号或密码错误");
            }
            request.getSession().setAttribute("User", one.getId());
            return R.success(one);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }

    }

    /**
     * 获取当前登录的用户
     *
     * @return
     */
    @Override
    public R getUser() {
        try {
            //从本地线程获取登录用户的id
            Integer userId = BaseContext.getCurrentId();
            log.info("本地id为：{}", userId);
            //根据用户id
            User u = this.getById(userId);
            return R.success(u);
        } catch (CustomException e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    /**
     * 更新用户手机
     *
     * @param user
     * @return
     */
    @Override
    public R updatePhone(User user) {
        try {
            //从本地线程获取当前登录用户的id
            Integer id = BaseContext.getCurrentId();
            //创建user对象
            user.setId(id);
            //更新电话
            boolean ret = this.updateById(user);
            return ret ? R.success("更新成功") : R.error("更新失败");
        } catch (CustomException e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    /**
     * 发送短信验证码
     *
     * @param phone
     * @param session
     * @return
     */
    @Override
    public R send(String phone, HttpSession session) {
        try {
            if (StringUtils.isNotEmpty(phone)) {
                String code = ValidateCodeUtils.generateValidateCode(4).toString();
                log.info("生成的验证码为：{}", code);
                SMSUtils.sendMessage("苏健昌的音乐", "SMS_462595788", phone, code);
                session.setAttribute(phone, code);
                return R.success("短信发送成功");
            }
            return R.error("登陆失败");
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    /**
     * 验证码登录
     *
     * @param userLoginDto
     * @param request
     * @return
     */
    @Override
    public R SMSLogin(UserLoginDto userLoginDto, HttpServletRequest request) {
        try {
            String codeSession = (String) request.getSession().getAttribute(userLoginDto.getPhone());
            String code = userLoginDto.getCode() + "";
            if (codeSession != null && codeSession.equals(code)) {
                QueryWrapper<User> query = Wrappers.query();
                query.eq("phone", userLoginDto.getPhone());
                User user = this.getOne(query);
                if (user == null) {
                    user = new User();
                    user.setPhone(userLoginDto.getPhone());
                    user.setUsername(userLoginDto.getPhone());
                    user.setPassword(userLoginDto.getPhone());
                    this.save(user);
                }
                //查找新创建用户的id
                user = this.getOne(query);
                request.setAttribute("User", user.getId());
                return R.success(user);
            }
            return R.error("登录失败");
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    /**
     * 获取登录验证码
     *
     * @param phone
     * @param session
     * @return
     */
    @Override
    public R getLoginCode(String phone, HttpSession session) {
        try {
            Object code = session.getAttribute(phone);
            log.info("验证码为：{}", code);
            return R.success(code);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }
}
