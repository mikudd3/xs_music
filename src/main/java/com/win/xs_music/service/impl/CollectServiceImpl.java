package com.win.xs_music.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.win.xs_music.common.BaseContext;
import com.win.xs_music.common.CustomException;
import com.win.xs_music.common.R;
import com.win.xs_music.mapper.CollectMapper;
import com.win.xs_music.mapper.SingerMapper;
import com.win.xs_music.mapper.SongListMapper;
import com.win.xs_music.pojo.Collect;
import com.win.xs_music.pojo.Singer;
import com.win.xs_music.pojo.SongList;
import com.win.xs_music.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private SingerMapper singerMapper;
    @Autowired
    private SongListMapper songListMapper;

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
            songLists = songListMapper.selectBatchIds(ids);
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
        return R.success(songLists);
    }


}
