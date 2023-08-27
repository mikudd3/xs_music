package com.win.xs_music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.win.xs_music.common.R;
import com.win.xs_music.pojo.Banner;

public interface BannerService extends IService<Banner> {


    /**
     * 获取轮播图
     *
     * @return
     */
    R getList();

    /**
     * 更新轮播图数据
     *
     * @param pic
     * @return
     */
    R updateBanner(Banner pic);
}
