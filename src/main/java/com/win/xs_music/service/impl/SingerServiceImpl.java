package com.win.xs_music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import com.win.xs_music.service.SingerService;
import com.win.xs_music.vo.MyCollectSingerVo;
import com.win.xs_music.vo.SingerCouerntVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ConvertingCursor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.win.xs_music.util.RedisConstants.*;

@Service
@Slf4j
public class SingerServiceImpl extends ServiceImpl<SingerMapper, Singer> implements SingerService {

    @Autowired
    private SingerMapper singerMapper;
    @Autowired
    private SongMapper songMapper;
    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取歌手地区分类
     *
     * @return
     */
    @Override
    public R getSingerLocationCategory() throws RuntimeException {
        Map<String, Long> map = new HashMap<>();
        //缓存中找不到数据则从数据库中获取
        List<Map<String, Object>> listMap = singerMapper.getSingerLocationCategory();
        log.info("查询到的数据为：{}", listMap);
        // 处理查询结果
        for (Map<String, Object> locationCount : listMap) {
            String location = (String) locationCount.get("location");
            Long count = (Long) locationCount.get("count");
            map.put(location, count);
        }
        log.info("查询到的map集合为：{}", map);
        //将歌手地区分类信息加入缓存中
        return R.success(map);
    }

    /**
     * 歌手信息分页查询
     *
     * @param currentPage
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public R getPage(Integer currentPage, Integer pageSize, String name) throws RuntimeException {
        Page<Singer> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Singer> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(name), Singer::getName, name);
        this.page(page, wrapper);
        return R.success(page);
    }

    /**
     * 删除歌手
     *
     * @param id
     * @return
     */
    @Override
    public R delete(Integer id) throws RuntimeException {
        //删除歌手的歌曲
        songMapper.deleteBySingerId(id);
        //删除歌手
        this.removeById(id);
        //3.删除缓存中关于歌手的所有数据
        deleteRedisKey();
        return R.success("删除成功");

    }

    /**
     * 获取歌手数量
     *
     * @return
     */
    @Override
    public R selectCount() throws RuntimeException {
        QueryWrapper<Singer> men = Wrappers.query();
        men.eq("sex", 1);
        int singerCountMan = this.count(men);
        //查找女歌手数量
        QueryWrapper<Singer> women = Wrappers.query();
        women.eq("sex", 0);
        int singerCountWomen = this.count(women);
        int singerCount = singerCountMan + singerCountWomen;
        SingerCouerntVo singerCouerntVo = new SingerCouerntVo(singerCountMan, singerCountWomen, singerCount);
        //将数据添加到redis缓存
        return R.success(singerCouerntVo);
    }

    /**
     * 根据id获取歌手信息
     *
     * @param id
     * @return
     */
    @Override
    public R getOne(Integer id) throws RuntimeException {
        Singer singer = songMapper.selectOne(id);
        MyCollectSingerVo collectSingerVo = new MyCollectSingerVo();
        BeanUtils.copyProperties(singer, collectSingerVo);
        //获取当前用户id
        Integer userId = BaseContext.getCurrentId();
        //根据当前用户id和歌手id判断当前歌手是否已经被当前用户已经关注
        Collect collect = collectMapper.selectMyLoveSingerWithUserIdAndSingerId(userId, id);
        //如果为空，表示没有关注过
        if (collect != null) {
            //将属性置为false
            collectSingerVo.setIsCollected(true);
        }
        return R.success(collectSingerVo);
    }

    /**
     * 获取歌手列表
     *
     * @param singer
     * @return
     */
    @Override
    public R getSingers(Singer singer) throws RuntimeException {
        String locationKey = SINGER_LIST + singer.getLocation() + singer.getSex();
        //1.先判断redis缓存中是否有该数据
        ArrayList<Singer> singerList = new ArrayList<>();
        singerList = (ArrayList<Singer>) redisTemplate.opsForValue().get(locationKey);
        if (singerList != null) {
            return R.success(singerList);
        }
        //2.如果缓存中没有数据，则向数据库中查找
        singerList = (ArrayList<Singer>) singerMapper.getSingers(singer);
        log.info("查询到的数据为：" + singerList);
        //将数据添加到redis缓存中
        redisTemplate.opsForValue().set(locationKey, singerList, 60, TimeUnit.MINUTES);
        // 处理查询结果
        return R.success(singerList);
    }


    /**
     * 添加歌手
     *
     * @param singer
     * @return
     */
    @Override
    public R add(Singer singer) throws RuntimeException {
        //删除缓存
        deleteRedisKey();
        //更新歌手信息
        boolean ret = this.save(singer);
        return ret ? R.success("添加成功") : R.error("添加失败");
    }

    /**
     * 更新歌手信息
     *
     * @param singer
     * @return
     */
    @Override
    public R updateSinger(Singer singer) throws RuntimeException {
        //删除缓存
        deleteRedisKey();
        boolean ret = this.updateById(singer);
        return ret ? R.success("更新成功") : R.error("更新失败");
    }

    private void deleteRedisKey() {
        //需要匹配的key
        String patternKey = SINGER_LIST + "*";
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
        try {
            cursor.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
