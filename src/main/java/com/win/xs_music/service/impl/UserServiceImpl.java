package com.win.xs_music.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.win.xs_music.mapper.UserMapper;
import com.win.xs_music.pojo.User;
import com.win.xs_music.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

}
