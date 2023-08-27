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
            return bannerService.getList();
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
        try {
            return bannerService.updateBanner(pic);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }

    }
}
