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
    public R getCollectSinger() throws RuntimeException {
        //从本地线程获取当前所登录的用户id
        Integer id = BaseContext.getCurrentId();
        //通过登录用户id查询用户的收藏的歌手id
        List<Integer> ids = collectMapper.getCollectSingerByUserId(id);
        if (ids == null) {
            //如果没有收藏歌单
            return R.success(null);
        }
        //根据获得的歌手id集合查询歌手数据
        List<Singer> singers = singerMapper.selectBatchIds(ids);
        //将得到的数据返回
        return R.success(singers);
    }

    /**
     * 获取当前登录用户所收藏的歌单
     *
     * @return
     */
    @Override
    public R getCollectSongList() throws RuntimeException {
        //从本地线程获取当前所登录的用户id
        Integer id = BaseContext.getCurrentId();
        //通过登录的id获取当前登录所收藏的歌单
        List<Integer> ids = collectMapper.getCollectSongListByUserId(id);
        log.info("111:{}", ids);

        if (ids == null || ids.isEmpty()) {
            //没有关注的歌手
            return R.success(null);
        }
        List<SongList> songLists = songListMapper.selectBatchIds(ids);
        return R.success(songLists);
    }

    /**
     * 获取我喜欢的音乐
     *
     * @return
     */
    @Override
    public R getMyLoveSong() throws RuntimeException {
        //先获取当前登录的用户id
        Integer userId = BaseContext.getCurrentId();
        //根据用户id获取收藏的歌的id
        List<Integer> ids = collectMapper.geMyLoveSongIdsByUserId(userId);
        if (ids == null || ids.isEmpty()) {
            return R.success(null);
        }
        //根据查询到歌曲id查询歌曲信息
        List<Song> songs = songMapper.selectBatchIds(ids);
        List<SongListVo> list = new ArrayList<>();
        for (Song song : songs) {
            String[] arr = song.getName().split("-");
            SongListVo songListVo = new SongListVo();
            BeanUtils.copyProperties(song, songListVo);
            songListVo.setName(arr[1]);
            songListVo.setSingerName(arr[0]);
            list.add(songListVo);
        }
        return R.success(list);
    }

    /**
     * 添加到我喜欢
     *
     * @param id
     * @return
     */
    @Override
    public R addMyLoveSong(Integer id) throws RuntimeException {
        //获取当前登录的用户
        Integer userId = BaseContext.getCurrentId();
        //创建Collect对象
        Collect collect = new Collect();
        //设置属性
        collect.setUserId(userId);
        collect.setSongId(id);
        collect.setType((byte) 0);
        //添加到数据库
        int ret = collectMapper.insert(collect);
        return ret > 0 ? R.success("添加成功") : R.error("添加失败");
    }

    /**
     * 取消添加到我的喜欢
     *
     * @param id
     * @return
     */
    @Override
    public R deleteMyLoveSong(Integer id) throws RuntimeException {
        //根据用户id和歌曲id删除数据
        //获取当前登录的用户
        Integer userId = BaseContext.getCurrentId();
        boolean ret = collectMapper.deleteWithUserIdAndSongId(userId, id);
        return ret ? R.success("取消成功") : R.error("取消失败");
    }

    /**
     * 收藏歌单
     *
     * @param id
     * @return
     */
    @Override
    public R collectSongList(Integer id) throws RuntimeException {
        Integer userId = BaseContext.getCurrentId();
        Collect collect = new Collect();
        //设置属性
        collect.setUserId(userId);
        collect.setSongListId(id);
        collect.setType((byte) 1);
        int ret = collectMapper.insert(collect);
        return ret > 0 ? R.success("收藏成功") : R.error("收藏失败");
    }

    /**
     * 取消收藏
     *
     * @param id
     * @return
     */
    @Override
    public R deleteMyCollectSongList(Integer id) throws RuntimeException {
        //获取当前登录用户
        Integer userId = BaseContext.getCurrentId();
        boolean b = collectMapper.deleteMyCollectSongListWithUserIdAndSongListId(userId, id);
        return b ? R.success("取消成功") : R.error("取消失败");
    }

    /**
     * 关注歌手
     *
     * @param id
     * @return
     */
    @Override
    public R addMyLoveSinger(Integer id) throws RuntimeException {
        //获取当前登录的用户
        Integer userId = BaseContext.getCurrentId();
        //创建Collect对象
        Collect collect = new Collect();
        //设置属性
        collect.setUserId(userId);
        collect.setSingerId(id);
        collect.setType((byte) 2);
        //添加到数据库
        int ret = collectMapper.insert(collect);
        return ret > 0 ? R.success("添加成功") : R.error("添加失败");
    }

    /**
     * 取消关注
     *
     * @param id
     * @return
     */
    @Override
    public R deleteMyLoveSinger(Integer id) throws RuntimeException {
        //根据用户id和歌曲id删除数据
        //获取当前登录的用户
        Integer userId = BaseContext.getCurrentId();
        boolean ret = collectMapper.deleteWithUserIdAndSingerId(userId, id);
        return ret ? R.success("取消成功") : R.error("取消失败");
    }
}
