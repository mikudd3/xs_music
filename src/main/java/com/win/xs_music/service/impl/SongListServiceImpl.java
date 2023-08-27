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
import com.win.xs_music.mapper.StyleMapper;
import com.win.xs_music.pojo.Collect;
import com.win.xs_music.pojo.ListSong;
import com.win.xs_music.pojo.SongList;
import com.win.xs_music.service.SongListService;
import com.win.xs_music.vo.GetMyCollectSongListVo;
import com.win.xs_music.vo.SongListPageVo;
import com.win.xs_music.vo.SongListflVo;
import com.win.xs_music.vo.gedanVo;
import lombok.extern.slf4j.Slf4j;
import org.ini4j.spi.Warnings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.ConvertingCursor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.win.xs_music.util.RedisConstants.*;

@Service
@Slf4j
@Transactional
public class SongListServiceImpl extends ServiceImpl<SongListMapper, SongList> implements SongListService {


    @Autowired
    private SongListMapper songListMapper;

    @Autowired
    private ListSongMapper listSongMapper;
    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private StyleMapper styleMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 歌单信息分页查询
     *
     * @param currentPage
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public R getPage(Integer currentPage, Integer pageSize, String name) throws RuntimeException {
        Page<SongList> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<SongList> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(name), SongList::getTitle, name);
        this.page(page, wrapper);
        //创建PageVo对象
        Page<SongListPageVo> listPage = new Page<>();
        BeanUtils.copyProperties(page, listPage, "records");
        //根据id查找风格
        List<SongList> records = page.getRecords();
        List<SongListPageVo> listRecords = new ArrayList<>();
        for (SongList record : records) {
            //根据id查询风格名
            SongListPageVo songListPageVo = new SongListPageVo();
            BeanUtils.copyProperties(record, songListPageVo);
            //查找风格名
            songListPageVo.setStyle(styleMapper.selectById(record.getStyleIds()).getStyleName());
            listRecords.add(songListPageVo);
        }
        listPage.setRecords(listRecords);
        return R.success(listPage);
    }

    /**
     * 获取歌单分类
     *
     * @return
     */
    @Override
    public R getStyle() throws RuntimeException {
        List<Map<String, Object>> maps = songListMapper.getStyle();
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

    /**
     * 获取歌单列表
     *
     * @param style_name
     * @return
     */
    @Override
    public ArrayList<SongListflVo> getSongList(String style_name) throws RuntimeException {
        //1.设置键值
        String key = SONGList_LIST + style_name;
        ArrayList<SongListflVo> maps = new ArrayList<>();
        //2.先从缓存中查询数据
        maps = (ArrayList<SongListflVo>) redisTemplate.opsForValue().get(key);
        //3.如果缓存中有数据
        if (maps != null) {
            return maps;
        }
        //4.如果缓存中没有该数据，则查询数据库
        if ("日韩".equals(style_name)) {
            //如果是日韩
            maps = songListMapper.getSongList1("日韩");
        } else {
            maps = songListMapper.getSongList(style_name);
        }
        log.info("查询到的数据为：{}", maps);
        redisTemplate.opsForValue().set(key, maps, 60, TimeUnit.MINUTES);
        return maps;
    }


    /**
     * 首页获取歌单数据
     *
     * @return
     */
    @Override
    public R getIndexSongList() throws RuntimeException {
        List<SongList> lists = new ArrayList<>();
        //先从缓存中查找
        lists = (List<SongList>) redisTemplate.opsForValue().get(INDEX_SONGList_LIST);
        if (lists != null) {
            return R.success(lists);
        }
        lists = songListMapper.getIndexSongList();
        //将查询到的数据添加到缓存中
        redisTemplate.opsForValue().set(INDEX_SONGList_LIST, lists, 60, TimeUnit.MINUTES);
        return R.success(lists);
    }

    /**
     * 根据歌单id获取歌单信息
     *
     * @param id
     * @return
     */
    @Override
    public R getOne(Integer id) throws RuntimeException {
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
    }

    /**
     * 获取我创建的歌单
     *
     * @return
     */
    @Override
    public R getMyCreateSongList() throws RuntimeException {
        Integer id = BaseContext.getCurrentId();
        //查询我创建的歌单
        QueryWrapper<SongList> query = Wrappers.query();
        query.eq("user_id", id);
        List<SongList> songLists = songListMapper.selectList(query);
        return R.success(songLists);
    }

    /**
     * 添加歌单
     *
     * @param songList
     * @return
     */
    @Override
    public R addSongList(SongList songList) throws RuntimeException {
        //删除缓存
        deleteRedisKey();
        //获取当前登录用户的id
        Integer user_id = BaseContext.getCurrentId();
        System.out.println(user_id);
        songList.setUser_id(user_id);
        boolean i = this.save(songList);
        return i ? R.success("添加成功") : R.error("添加失败");
    }

    /**
     * 更新歌单数据
     *
     * @param songList
     * @return
     */
    @Override
    public R updateSongList(SongList songList) throws RuntimeException {
        //删除缓存
        deleteRedisKey();
        boolean ret = this.updateById(songList);
        return ret ? R.success("更新成功") : R.error("更新失败");
    }

    /**
     * 删除歌单
     *
     * @param id
     * @return
     */
    @Override
    public R removeSongList(Integer id) throws RuntimeException {
        //删除缓存
        deleteRedisKey();
        boolean ret = this.removeById(id);
        return ret ? R.success("删除成功") : R.success("删除失败");
    }


    private void deleteRedisKey() {
        //需要匹配的key
        String patternKey = SONGList_LIST + "*";
        ScanOptions options = ScanOptions.scanOptions()
                .match(patternKey).build();
        RedisSerializer<String> redisSerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
        Cursor cursor = (Cursor) redisTemplate.executeWithStickyConnection(
                redisConnection -> new ConvertingCursor<>(redisConnection.scan(options), redisSerializer::deserialize));
        List<String> result = new ArrayList<>();
        while (cursor.hasNext()) {
            result.add(cursor.next().toString());
        }
        log.info("键为：{}", result);
        //删除key
        redisTemplate.delete(result);
        redisTemplate.delete(INDEX_SONGList_LIST);
        try {
            cursor.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
