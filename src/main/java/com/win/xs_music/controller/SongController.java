package com.win.xs_music.controller;


import com.win.xs_music.common.R;
import com.win.xs_music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongService songService;

    //查找歌曲数量
    @GetMapping("/getSongCount")
    public R getSongCount() {
        int count = songService.count();
        return R.success(count);
    }


}
