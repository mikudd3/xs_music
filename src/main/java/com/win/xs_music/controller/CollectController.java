package com.win.xs_music.controller;

import com.win.xs_music.common.CustomException;
import com.win.xs_music.common.R;
import com.win.xs_music.service.CollectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collect")
@Slf4j
public class CollectController {

    @Autowired
    private CollectService collectService;

    //获取当前登录用户所关注的歌手
    @GetMapping("/getCollectSinger")
    public R getCollectSinger() {
        try {
            return collectService.getCollectSinger();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }


    //获取当前登录用户所收藏的歌单
    @GetMapping("/getCollectSongList")
    public R getCollectSongList() {
        try {
            return collectService.getCollectSongList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }


    //获取我喜欢的音乐
    @GetMapping("/getMyLoveSong")
    public R getMyLoveSong() {
        try {
            return collectService.getMyLoveSong();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }


    //添加到我喜欢
    @PostMapping("/addMyLoveSong")
    public R addMyLoveSong(Integer id) {
        try {
            log.info("获取的id为：{}", id);
            return collectService.addMyLoveSong(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }


    //取消添加到我的喜欢
    @PostMapping("/deleteMyLoveSong")
    public R deleteMyLoveSong(Integer id) {
        try {
            log.info("获取的id为：{}", id);
            return collectService.deleteMyLoveSong(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }


    //执行收藏歌单功能
    @PostMapping("/collectSongList")
    public R collectSongList(Integer id) {
        try {
            log.info("获得的id为：{}", id);
            return collectService.collectSongList(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    //执行取消收藏功能
    @PostMapping("/deleteMyCollectSongList")
    public R deleteMyCollectSongList(Integer id) {
        try {
            log.info("获得的id为：{}", id);
            return collectService.deleteMyCollectSongList(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    //关注歌手
    @PostMapping("/addMyLoveSinger")
    public R addMyLoveSinger(Integer id) {
        try {
            log.info("获得的歌手id为：{}", id);
            return collectService.addMyLoveSinger(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    //取消关注歌手
    @PostMapping("/deleteMyLoveSinger")
    public R deleteMyLoveSinger(Integer id) {
        try {
            log.info("获得的歌手id为：{}", id);
            return collectService.deleteMyLoveSinger(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }

}
