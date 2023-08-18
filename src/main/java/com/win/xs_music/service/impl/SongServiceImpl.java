package com.win.xs_music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.win.xs_music.common.CustomException;
import com.win.xs_music.common.R;
import com.win.xs_music.mapper.SingerMapper;
import com.win.xs_music.mapper.SongMapper;
import com.win.xs_music.pojo.Singer;
import com.win.xs_music.pojo.Song;
import com.win.xs_music.service.SongService;
import com.win.xs_music.view.SongView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {
    @Autowired
    private SingerMapper singerMapper;

    @Override
    public R updateSong(SongView songView) {
        //根据歌手名获取歌手信息
        boolean ret = false;
        try {
            Singer singer = singerMapper.selectByName(songView.getSingerName());
            if (singer == null) {
                return R.error("修改失败，歌手不存在，请先添加歌手");
            }
            Song song = new Song();
            BeanUtils.copyProperties(songView, song);
            song.setSingerId(singer.getId());
            ret = this.updateById(song);
        } catch (BeansException e) {
            throw new CustomException("系统错误，请联系管理员");
        }
        return ret ? R.success("更新成功") : R.error("更新失败");
    }

    @Override
    public R getSingerName() {
        List<String> singerName = null;
        try {
            singerName = singerMapper.getSingerName();
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
        log.info("获得的歌手名为：{}", singerName);
        return R.success(singerName);
    }

    @Override
    public R selectList(Integer id) {
        List<Song> songs = null;
        try {
            songs = singerMapper.selectList(id);
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
        log.info("查到了：{}首歌曲",songs.size());
        for (int i = 0; i < songs.size(); i++) {
            String[] arr = songs.get(i).getName().split("-");
            songs.get(i).setName(arr[1]);
        }
        return R.success(songs);
    }
}
