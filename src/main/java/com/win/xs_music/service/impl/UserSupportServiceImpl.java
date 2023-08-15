package com.win.xs_music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.win.xs_music.common.R;
import com.win.xs_music.mapper.UserSupportMapper;
import com.win.xs_music.pojo.UserSupport;
import com.win.xs_music.service.UserSupportService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSupportServiceImpl extends ServiceImpl<UserSupportMapper, UserSupport>
        implements UserSupportService {


}
