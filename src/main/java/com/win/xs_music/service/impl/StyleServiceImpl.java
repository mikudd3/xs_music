package com.win.xs_music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.win.xs_music.common.R;
import com.win.xs_music.mapper.StyleMapper;
import com.win.xs_music.pojo.SongStyle;
import com.win.xs_music.service.StyleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @project:
 * @author: fqq
 * @version: 1.0
 */
@Slf4j
@Service
public class StyleServiceImpl implements StyleService {

    @Autowired
    private StyleMapper styleMapper;

    @Override
    public R getStyleName() {
        QueryWrapper<SongStyle> query = Wrappers.query();
        List<SongStyle> songStyles = styleMapper.selectList(query);
        log.info("查到的风格：{}", songStyles);
        return R.success(songStyles);
    }
}
