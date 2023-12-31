package com.win.xs_music.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
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

    /**
     * 更新歌曲信息
     *
     * @param songView
     * @return
     */
    @Override
    public R updateSong(SongView songView) throws RuntimeException {
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
    }

    /**
     * 获取歌手名字
     *
     * @return
     */
    @Override
    public R getSingerName() throws RuntimeException {
        List<String> singerName = singerMapper.getSingerName();
        log.info("获得的歌手名为：{}", singerName);
        return R.success(singerName);
    }

    /**
     * 查询歌手的歌曲列表
     *
     * @param id
     * @return
     */
    @Override
    public R selectList(Integer id) throws RuntimeException {
        //获取当前登录用户
        Integer userId = BaseContext.getCurrentId();
        //获取歌手歌曲
        List<SongListVo> songs = songMapper.selectList(id);
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
    }

    /**
     * 查询歌单的歌曲列表
     *
     * @param id
     * @return
     */
    @Override
    public R selectList1(Integer id) throws RuntimeException {
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
    }

    /**
     * 通过歌曲名字获取歌曲信息
     *
     * @param songname
     * @return
     */
    @Override
    public R getSongByName(String songname) throws RuntimeException {
        if (!StringUtils.isNotEmpty(songname)) {
            return R.error("请输入查询条件");
        }
        //获取本地登录用户
        Integer userId = BaseContext.getCurrentId();
        //根据用户名模糊查询
        songname = "%" + songname + "%";
        List<Song> songs = songMapper.getListBySongName(songname);
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
    }

    /**
     * 获取当前正在播放的音乐信息
     *
     * @param id
     * @return
     */
    @Override
    public R getSong(Integer id) throws RuntimeException {
        Song song = this.getById(id);
        SongListVo songListVo = new SongListVo();
        BeanUtils.copyProperties(song, songListVo);
        String[] arr = song.getName().split("-");
        songListVo.setName(arr[1]);
        songListVo.setSingerName(arr[0]);
        return R.success(songListVo);
    }
}
