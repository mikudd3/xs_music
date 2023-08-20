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
import com.win.xs_music.service.SingerService;
import com.win.xs_music.vo.MyCollectSingerVo;
import com.win.xs_music.vo.SingCountVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SingerServiceImpl extends ServiceImpl<SingerMapper, Singer> implements SingerService {

    @Autowired
    private SingerMapper singerMapper;
    @Autowired
    private SongMapper songMapper;
    @Autowired
    private CollectMapper collectMapper;

    @Override
    public R getSingerLocationCategory() {
        List<Map<String, Object>> listMap = null;
        try {
            listMap = singerMapper.getSingerLocationCategory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
        log.info("查询到的数据为：{}", listMap);
        Map<String, Long> map = new HashMap<>();
        // 处理查询结果
        for (Map<String, Object> locationCount : listMap) {
            String location = (String) locationCount.get("location");
            Long count = (Long) locationCount.get("count");
            map.put(location, count);
        }
        log.info("查询到的map集合为：{}", map);
        return R.success(map);
    }

    //歌手信息分页查询
    @Override
    public R getPage(Integer currentPage, Integer pageSize, String name) {
        Page<Singer> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Singer> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(name), Singer::getName, name);
        try {
            this.page(page, wrapper);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
        return R.success(page);
    }

    // 删除歌手
    @Override
    public R delete(Integer id) {
        //删除歌手的歌曲
        try {
            songMapper.getListBySingerId(id);
            //删除歌手
            this.removeById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
        return R.success("删除成功");
    }

    @Override
    public R selectCount() {
        //查找男歌手数量
        SingCountVo singCountVo = null;
        try {
            QueryWrapper<Singer> men = Wrappers.query();
            men.eq("sex", 1);
            int singerCountMan = this.count(men);
            //查找女歌手数量
            QueryWrapper<Singer> women = Wrappers.query();
            women.eq("sex", 0);
            int singerCountWomen = this.count(women);
            int singerCount = singerCountMan + singerCountWomen;
            singCountVo = new SingCountVo(singerCountMan, singerCountWomen, singerCount);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
        return R.success(singCountVo);
    }

    @Override
    public R getOne(Integer id) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }


    }


    @Override
    public R getSingers(Singer singer) {
        List<Map<String, Object>> singerList = null;
        try {
            singerList = singerMapper.getSingers(singer);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
        // 处理查询结果
        return R.success(singerList);
    }


}
