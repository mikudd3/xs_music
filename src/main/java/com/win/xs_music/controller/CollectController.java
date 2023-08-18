package com.win.xs_music.controller;

import com.win.xs_music.common.CustomException;
import com.win.xs_music.common.R;
import com.win.xs_music.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    private CollectService collectService;

    //获取当前登录用户所关注的歌手
    @GetMapping("/getCollectSinger")
    public R getCollectSinger() {
        try {
            return collectService.getCollectSinger();
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
    }


    //获取当前登录用户所收藏的歌单
    @GetMapping("/getCollectSongList")
    public R getCollectSongList() {
        try {
            return collectService.getCollectSongList();
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
    }


}
