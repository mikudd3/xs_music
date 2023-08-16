package com.win.xs_music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.win.xs_music.common.R;
import com.win.xs_music.pojo.User;

public interface UserService extends IService<User> {

    //
    R getPage(Integer currentPage, Integer pageSize, User user);
}
