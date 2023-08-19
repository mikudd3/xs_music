package com.win.xs_music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.win.xs_music.common.BaseContext;
import com.win.xs_music.common.CustomException;
import com.win.xs_music.common.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.win.xs_music.mapper.CollectMapper;
import com.win.xs_music.mapper.ListSongMapper;
import com.win.xs_music.mapper.SongListMapper;
import com.win.xs_music.pojo.Collect;
import com.win.xs_music.pojo.ListSong;
import com.win.xs_music.pojo.SongList;
import com.win.xs_music.service.SongListService;
import com.win.xs_music.vo.GetMyCollectSongListVo;
import com.win.xs_music.vo.SongListflVo;
import com.win.xs_music.vo.gedanVo;
import lombok.extern.slf4j.Slf4j;
import org.ini4j.spi.Warnings;
import org.springframework.beans.BeanUtils;
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
    private SongListMapper songListMapper;

    @Autowired
    private ListSongMapper listSongMapper;
    @Autowired
    private CollectMapper collectMapper;

    //歌手信息分页查询
    @Override
    public R getPage(Integer currentPage, Integer pageSize, String name) {
        Page<SongList> page = null;
        try {
            page = new Page<>(currentPage, pageSize);
            LambdaQueryWrapper<SongList> wrapper = new LambdaQueryWrapper<>();
            wrapper.like(StringUtils.isNotEmpty(name), SongList::getTitle, name);
            this.page(page, wrapper);
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
        return R.success(page);
    }

    @Override
    public R getStyle() {
        List<Map<String, Object>> maps = null;
        try {
            maps = songListMapper.getStyle();
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
        log.info("查询到的数据为：{}", maps);
        Map<String, Long> map = new HashMap<>();
        //处理查询结果
        for (Map<String, Object> styleCount : maps) {
            String style = (String) styleCount.get("name");
            Long count = (Long) styleCount.get("count");
            map.put(style, count);
        }
        log.info("查询到的map集合为：{}", map);
        return R.success(map);
    }


   /* @Override
    public ArrayList<SongListflVo> getSongList(String style_name) {
        ArrayList<SongListflVo> maps = null;
        try {
            maps = songListMapper.getSongList(style_name);
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
        log.info("查询到的数据为：{}", maps);
        return maps;
    }*/


    @Override
    public ArrayList<SongListflVo> getSongList(String style_name) {
        ArrayList<SongListflVo> maps = null;
        try {
            System.out.println(!"日韩".equals(style_name));
            if (style_name == null && !"日韩".equals(style_name)) {
                maps = songListMapper.getSongList(style_name);
            } else {
                maps = songListMapper.getSongList1("日韩");
            }
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
        log.info("查询到的数据为：{}", maps);
        return maps;
    }


    /**
     * 首页获取歌单数据
     *
     * @return
     */
    @Override
    public R getIndexSongList() {
        List<SongList> lists = null;
        try {
            lists = songListMapper.getIndexSongList();
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
        return R.success(lists);
    }


    @Override
    public R getOne(Integer id) {
        try {
            //获取当前登录用户
            Integer userId = BaseContext.getCurrentId();
            //查询歌单详细信息
            SongList songList = songListMapper.selectById(id);
            GetMyCollectSongListVo songListVo = new GetMyCollectSongListVo();
            BeanUtils.copyProperties(songList, songListVo);
            //判断当前用户是否已经登录
            if (userId != null) {
                //如果已经登录则查询当前歌单是否已经被当前登录的用户收藏
                Collect collect = collectMapper.getCollectSongListWithUserIdAndSongListId(userId, songList.getId());
                songListVo.setIsCollected(collect != null);
            }
            return R.success(songListVo);
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    @Override
    public R getMyCreateSongList() {
        try {
            Integer id = BaseContext.getCurrentId();
            //查询我创建的歌单
            QueryWrapper<SongList> query = Wrappers.query();
            query.eq("user_id", id);
            List<SongList> songLists = songListMapper.selectList(query);
            return R.success(songLists);
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    @Override
    public R addSongList(SongList songList) {
        try {
            //获取当前登录用户的id
            Integer user_id = BaseContext.getCurrentId();
            System.out.println(user_id);
            songList.setUser_id(user_id);
            int i = songListMapper.insert(songList);
            return i>0? R.success("添加成功"):R.error("添加失败");
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
    }

}
