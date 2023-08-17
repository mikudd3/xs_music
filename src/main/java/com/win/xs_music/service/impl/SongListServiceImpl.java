package com.win.xs_music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.win.xs_music.common.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.win.xs_music.mapper.SongListMapper;
import com.win.xs_music.pojo.SongList;
import com.win.xs_music.service.SongListService;
import com.win.xs_music.vo.SongListflVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SongListServiceImpl extends ServiceImpl<SongListMapper, SongList> implements SongListService {


    @Autowired
    SongListMapper songListMapper;

    //歌手信息分页查询
    @Override
    public R getPage(Integer currentPage, Integer pageSize, String name) {
        Page<SongList> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<SongList> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(name), SongList::getTitle, name);
        this.page(page, wrapper);
        return R.success(page);
    }

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


    @Override
    public ArrayList<SongListflVo> getSongList(String style_name) {
        ArrayList<SongListflVo> maps = songListMapper.getSongList(style_name);
        log.info("查询到的数据为：{}", maps);
        return maps;
    }
}
