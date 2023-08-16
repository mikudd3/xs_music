package com.win.xs_music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.win.xs_music.common.R;
import com.win.xs_music.mapper.SingerMapper;
import com.win.xs_music.mapper.view.SongViewMapper;
import com.win.xs_music.pojo.Singer;
import com.win.xs_music.pojo.Song;
import com.win.xs_music.service.SingerService;
import com.win.xs_music.service.SongService;
import com.win.xs_music.service.SongViewService;
import com.win.xs_music.view.SongView;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @project:
 * @author: mikudd3
 * @version: 1.0
 */
@Service
@Slf4j
public class SongViewServiceImpl extends ServiceImpl<SongViewMapper, SongView> implements SongViewService {
    @Autowired
    private SongViewMapper songViewMapper;
    @Autowired
    private SingerMapper singerMapper;
    @Autowired
    private SongService songService;

    //获取歌手名

    @Override
    public R getPage(Integer currentPage, Integer pageSize, String singerName) {
        Page<SongView> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<SongView> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotEmpty(singerName), SongView::getSingerName, singerName);
        this.page(page, wrapper);
        return R.success(page);
    }

    //添加歌曲
    @Override
    public R add(SongView songView) {
        //根据歌手名获取歌手id
        Singer singer = singerMapper.selectByName(songView.getSingerName());
        //判断该歌手已经存在
        if (singer == null) {
            return R.error("该歌手不存在，请先创建歌手");
        }
        Song song = new Song();
        BeanUtils.copyProperties(songView, song);
        //设置歌手id
        song.setSingerId(singer.getId());
        boolean ret = songService.save(song);
        return ret ? R.success("添加成功") : R.error("添加失败");
    }
}
