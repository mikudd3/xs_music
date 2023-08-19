package com.win.xs_music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.win.xs_music.common.CustomException;
import com.win.xs_music.common.R;
import com.win.xs_music.mapper.ListSongMapper;
import com.win.xs_music.pojo.ListSong;
import com.win.xs_music.service.ListSongService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListSongServiceImpl extends ServiceImpl<ListSongMapper, ListSong> implements ListSongService {

    @Autowired
    private ListSongMapper listSongMapper;

    @Override
    public R add(Integer songId, Integer songListId) {
        try {
            ListSong listSong = new ListSong();
            listSong.setSongListId(songListId);
            listSong.setSongId(songId);
            //先判断是否已经存在
            ListSong ls = listSongMapper.selectWithSongIdAndSongListId(songId, songListId);
            if (ls != null) {
                //已经收藏过，不可以重复收藏
                return R.error("不可重复收藏");
            }
            listSongMapper.insert(listSong);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
        return null;
    }
}
