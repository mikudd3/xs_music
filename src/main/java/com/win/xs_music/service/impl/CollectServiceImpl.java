package com.win.xs_music.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.win.xs_music.common.BaseContext;
import com.win.xs_music.common.CustomException;
import com.win.xs_music.common.R;
import com.win.xs_music.mapper.CollectMapper;
import com.win.xs_music.mapper.SingerMapper;
import com.win.xs_music.mapper.SongListMapper;
import com.win.xs_music.mapper.SongMapper;
import com.win.xs_music.pojo.Collect;
import com.win.xs_music.pojo.Singer;
import com.win.xs_music.pojo.Song;
import com.win.xs_music.pojo.SongList;
import com.win.xs_music.service.CollectService;
import com.win.xs_music.vo.SongListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private SingerMapper singerMapper;
    @Autowired
    private SongListMapper songListMapper;
    @Autowired
    private SongMapper songMapper;

    /**
     * 获取当前登录用户所关注的歌手
     *
     * @return
     */
    @Override
    public R getCollectSinger() {
        //从本地线程获取当前所登录的用户id
        Integer id = BaseContext.getCurrentId();
        //通过登录用户id查询用户的收藏的歌手id
        List<Integer> ids = null;
        List<Singer> singers = null;
        try {
            ids = collectMapper.getCollectSingerByUserId(id);
            if (ids == null) {
                //如果没有收藏歌单
                return R.success(null);
            }
            //根据获得的歌手id集合查询歌手数据
            singers = singerMapper.selectBatchIds(ids);

        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
        //将得到的数据返回
        return R.success(singers);
    }

    /**
     * 获取当前登录用户所收藏的歌单
     *
     * @return
     */
    @Override
    public R getCollectSongList() {
        //从本地线程获取当前所登录的用户id
        Integer id = BaseContext.getCurrentId();
        //通过登录的id获取当前登录所收藏的歌单
        List<Integer> ids = null;
        List<SongList> songLists = null;
        try {
            ids = collectMapper.getCollectSongListByUserId(id);
            if (ids == null) {
                //没有关注的歌手
                return R.success(null);
            }
            songLists = songListMapper.selectBatchIds(ids);

        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
        return R.success(songLists);
    }

    /**
     * 获取我喜欢的音乐
     *
     * @return
     */
    @Override
    public R getMyLoveSong() {
        try {
            //先获取当前登录的用户id
            Integer userId = BaseContext.getCurrentId();
            //根据用户id获取收藏的歌的id
            List<Integer> ids = collectMapper.geMyLoveSongIdsByUserId(userId);
            if (ids == null) {
                return R.success(null);
            }
            //根据查询到歌曲id查询歌曲信息
            List<Song> songs = songMapper.selectBatchIds(ids);
            List<SongListVo>  list = new ArrayList<>();
            for (int i = 0; i < songs.size(); i++) {
                String[] arr = songs.get(i).getName().split("-");
                SongListVo songListVo = new SongListVo();
                BeanUtils.copyProperties(songs.get(i), songListVo);
                songListVo.setName(arr[1]);
                songListVo.setSingerName(arr[0]);
                list.add(songListVo);
            }
            return R.success(list);
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
    }


}
