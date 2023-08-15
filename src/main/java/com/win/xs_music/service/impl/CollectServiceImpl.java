package com.win.xs_music.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.win.xs_music.mapper.CollectMapper;
import com.win.xs_music.pojo.Collect;
import com.win.xs_music.service.CollectService;
import org.springframework.stereotype.Service;

@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {
}
