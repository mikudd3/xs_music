package com.win.xs_music.controller;

import com.win.xs_music.common.CustomException;
import com.win.xs_music.common.R;
import com.win.xs_music.pojo.Banner;
import com.win.xs_music.service.BannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banner")
@Slf4j
public class BannerController {
    @Autowired
    private BannerService bannerService;

    /**
     * 获取轮播图
     * @return
     */
    @GetMapping("/getlbt")
    public R getBanner() {
        try {
            List<Banner> list = bannerService.list();
            log.info("获取到的轮播图数据为：{}", list);
            return R.success(list);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }

    }


    /**
     * 更新轮播图数据
     * @param pic
     * @return
     */
    @PutMapping("/update")
    public R update(@RequestBody Banner pic) {
        log.info("更新歌手传入信息：{}", pic);
        try {
            boolean ret = bannerService.updateById(pic);
            return ret ? R.success("更新成功") : R.error("更新失败");
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }

    }
}
