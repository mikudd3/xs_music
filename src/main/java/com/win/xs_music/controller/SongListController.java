package com.win.xs_music.controller;


import com.win.xs_music.common.R;
import com.win.xs_music.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/songlist")
public class SongListController {

    @Autowired
    private SongListService songListService;

    @GetMapping("/getSongListCount")
    public R getSongListCount() {
        int count = songListService.count();
        return R.success(count);
    }


}
