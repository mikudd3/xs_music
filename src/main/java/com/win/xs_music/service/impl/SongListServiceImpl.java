package com.win.xs_music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.win.xs_music.common.R;
import com.win.xs_music.mapper.SongListMapper;
import com.win.xs_music.pojo.Singer;
import com.win.xs_music.pojo.SongList;
import com.win.xs_music.service.SongListService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SongListServiceImpl extends ServiceImpl<SongListMapper, SongList> implements SongListService {


    @Autowired
    SongListMapper songListMapper;

    @Override
    public R getStyle() {
        List<Map<String, Object>> maps = songListMapper.getStyle();
        log.info("查询到的数据为：{}", maps);
        Map<String,Long> map = new HashMap<>();
        //处理查询结果
        for (Map<String,Object> styleCount : maps){
            String style = (String) styleCount.get("name");
            Long count = (Long) styleCount.get("count");
            map.put(style,count);
        }
        log.info("查询到的map集合为：{}", map);
        return R.success(map);
    }

    //歌手信息分页查询
    @Override
    public R getPage(Integer currentPage, Integer pageSize, String name) {
        Page<SongList> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<SongList> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(name), SongList::getTitle, name);
        this.page(page, wrapper);
        return R.success(page);
    }

}
