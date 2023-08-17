package com.win.xs_music.controller;

import com.win.xs_music.common.R;
import com.win.xs_music.pojo.Banner;
import com.win.xs_music.service.BannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 祝英台炸油条
 * @Time : 2022/6/13 13:16
 **/
@RestController
@RequestMapping("/banner")
@Slf4j
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @GetMapping("/getlbt")
    public R getBanner() {
        List<Banner> list = bannerService.list();
        log.info("获取到的轮播图数据为：{}", list);
        return R.success(list);
    }


    //更新数据
    @PutMapping("/update")
    public R update(@RequestBody Banner pic) {
        log.info("更新歌手传入信息：{}", pic);
        boolean ret = bannerService.updateById(pic);
        return ret ? R.success("更新成功") : R.error("更新失败");
    }
}
