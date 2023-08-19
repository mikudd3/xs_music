package com.win.xs_music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.win.xs_music.common.BaseContext;
import com.win.xs_music.common.CustomException;
import com.win.xs_music.common.R;
import com.win.xs_music.mapper.CollectMapper;
import com.win.xs_music.mapper.SingerMapper;
import com.win.xs_music.mapper.SongMapper;
import com.win.xs_music.pojo.Collect;
import com.win.xs_music.pojo.Singer;
import com.win.xs_music.pojo.Song;
import com.win.xs_music.service.SongService;
import com.win.xs_music.view.SongView;
import com.win.xs_music.vo.GetListSongVo;
import com.win.xs_music.vo.GetSingerSongVo;
import com.win.xs_music.vo.SongListVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {
    @Autowired
    private SingerMapper singerMapper;
    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private SongMapper songMapper;

    @Override
    public R updateSong(SongView songView) {
        try {
            //根据歌手名获取歌手信息
            Singer singer = singerMapper.selectByName(songView.getSingerName());
            if (singer == null) {
                return R.error("修改失败，歌手不存在，请先添加歌手");
            }
            Song song = new Song();
            BeanUtils.copyProperties(songView, song);
            song.setSingerId(singer.getId());
            boolean ret = this.updateById(song);
            return ret ? R.success("更新成功") : R.error("更新失败");
        } catch (BeansException e) {
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    @Override
    public R getSingerName() {
        try {
            List<String> singerName = singerMapper.getSingerName();
            log.info("获得的歌手名为：{}", singerName);
            return R.success(singerName);
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    @Override
    public R selectList(Integer id) {
        try {
            //获取当前登录用户
            Integer userId = BaseContext.getCurrentId();
            //获取歌手歌曲
            List<Song> songs = songMapper.selectList(id);
            List<GetSingerSongVo> list = new ArrayList<>();
            for (Song song : songs) {
                String[] arr = song.getName().split("-");
                song.setName(arr[1]);
                GetSingerSongVo singerSongVo = new GetSingerSongVo();
                BeanUtils.copyProperties(song, singerSongVo);
                //如果当前用户已登录
                if (userId != null) {
                    //通过当前登录用户和歌曲id查询是否是当前用户已喜欢的歌曲
                    Collect collect = collectMapper.getMyLoveSongWithUserIdAndSongId(userId, song.getId());
                    //true表示不喜欢，false表示该歌曲已被用户添加到我喜欢
                    singerSongVo.setLike(collect == null);
                } else {
                    singerSongVo.setLike(true);
                }
                list.add(singerSongVo);
            }
            return R.success(list);
        } catch (BeansException e) {
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    @Override
    public R selectList1(Integer id) {
        try {
            //获取当前登录用户
            Integer userId = BaseContext.getCurrentId();
            List<SongListVo> songs = songMapper.selectList1(id);
            List<GetListSongVo> list = new ArrayList<>();
            for (SongListVo song : songs) {
                //格式化歌名
                String[] arr = song.getName().split("-");
                song.setName(arr[1]);
                song.setSingerName(arr[0]);
                GetListSongVo getListSongVo = new GetListSongVo();
                BeanUtils.copyProperties(song, getListSongVo);
                //如果当前用户已登录
                if (userId != null) {
                    //通过当前登录用户和歌曲id查询是否是当前用户已喜欢的歌曲
                    Collect collect = collectMapper.getMyLoveSongWithUserIdAndSongId(userId, song.getId());
                    //true表示不喜欢，false表示该歌曲已被用户添加到我喜欢
                    getListSongVo.setLike(collect == null);
                } else {
                    getListSongVo.setLike(true);
                }
                list.add(getListSongVo);
            }
            return R.success(list);
        } catch (BeansException e) {
            throw new CustomException("系统错误，请联系管理员");
        }
    }


    @Override
    public R getSongByName(String songname) {
        try {
            if (!StringUtils.isNotEmpty(songname)) {
                return R.error("请输入查询条件");
            }
            //获取本地登录用户
            Integer userId = BaseContext.getCurrentId();
            //根据用户名模糊查询
            LambdaQueryWrapper<Song> wrapper = new LambdaQueryWrapper<>();
            wrapper.like(Song::getName, songname);
            List<Song> songs = this.list(wrapper);
            //遍历
            List<GetListSongVo> list = new ArrayList<>();
            for (Song song : songs) {
                //格式化歌名
                String[] arr = song.getName().split("-");
                song.setName(arr[1]);
                GetListSongVo getListSongVo = new GetListSongVo();
                BeanUtils.copyProperties(song, getListSongVo);
                getListSongVo.setSingerName(arr[0]);
                //如果当前用户已登录
                if (userId != null) {
                    //通过当前登录用户和歌曲id查询是否是当前用户已喜欢的歌曲
                    Collect collect = collectMapper.getMyLoveSongWithUserIdAndSongId(userId, song.getId());
                    //true表示不喜欢，false表示该歌曲已被用户添加到我喜欢
                    getListSongVo.setLike(collect == null);
                } else {
                    getListSongVo.setLike(true);
                }
                list.add(getListSongVo);
            }
            return R.success(list);
        } catch (BeansException e) {
            throw new CustomException("系统错误，请联系管理员");
        }
    }
}
