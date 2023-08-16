package com.win.xs_music.controller;


import com.win.xs_music.common.R;
import com.win.xs_music.service.SongListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/songlist")
@Slf4j
public class SongListController {

    @Autowired
    private SongListService songListService;

    @GetMapping("/getSongListCount")
    public R getSongListCount() {
        int count = songListService.count();
        return R.success(count);
    }



    //获取歌单分类的数量
    @GetMapping("/getStyle")
    public R getStyle(){
        R result = songListService.getStyle();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!1");
        log.info(String.valueOf(result));
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!2");
        return result;
    }
}
