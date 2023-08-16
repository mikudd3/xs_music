package com.win.xs_music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.win.xs_music.common.R;
import com.win.xs_music.mapper.SingerMapper;
import com.win.xs_music.mapper.SongMapper;
import com.win.xs_music.pojo.Singer;
import com.win.xs_music.pojo.Song;
import com.win.xs_music.service.SingerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SingerServiceImpl extends ServiceImpl<SingerMapper, Singer> implements SingerService {

    @Autowired
    private SingerMapper singerMapper;
    @Autowired
    private SongMapper songMapper;

    @Override
    public R getSingerLocationCategory() {
        List<Map<String, Object>> listMap = singerMapper.getSingerLocationCategory();
        log.info("查询到的数据为：{}", listMap);
        Map<String, Long> map = new HashMap<>();
        // 处理查询结果
        for (Map<String, Object> locationCount : listMap) {
            String location = (String) locationCount.get("location");
            Long count = (Long) locationCount.get("count");
            map.put(location, count);
        }
        log.info("查询到的map集合为：{}", map);
        return R.success(map);
    }

    //歌手信息分页查询
    @Override
    public R getPage(Integer currentPage, Integer pageSize, String name) {
        Page<Singer> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Singer> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(name), Singer::getName, name);
        this.page(page, wrapper);
        return R.success(page);
    }

    // 删除歌手
    @Override
    public R delete(Integer id) {
        //删除歌手对应的歌曲
        songMapper.deleteBySingerId(id);
        //删除歌手
        this.removeById(id);
        return R.success("删除成功");
    }
}
