package com.win.xs_music.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.win.xs_music.common.R;
import com.win.xs_music.mapper.BannerMapper;
import com.win.xs_music.pojo.Banner;
import com.win.xs_music.service.BannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.win.xs_music.util.RedisConstants.BANNER;


@Service
@Slf4j
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner>
        implements BannerService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取轮播图
     *
     * @return
     */
    @Override
    public R getList() throws RuntimeException {
        //先从缓存中获取数据
        List<Banner> list = new ArrayList<>();
        list = (List<Banner>) redisTemplate.opsForValue().get(BANNER);
        if (list != null) {
            return R.success(list);
        }
        list = this.list();
        log.info("获取到的轮播图数据为：{}", list);
        //将数据保存到缓存中
        //保存时间为60分钟
        redisTemplate.opsForValue().set(BANNER, list, 60, TimeUnit.MINUTES);
        return R.success(list);
    }

    /**
     * 更新轮播图数据
     *
     * @param pic
     * @return
     */
    @Override
    public R updateBanner(Banner pic) throws RuntimeException {
        //先从缓存中将数据删除
        redisTemplate.delete(BANNER);
        //更新数据
        boolean ret = this.updateById(pic);
        return ret ? R.success("更新成功") : R.error("更新失败");
    }
}
