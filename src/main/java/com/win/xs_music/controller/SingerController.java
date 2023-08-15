package com.win.xs_music.controller;


import com.win.xs_music.common.R;
import com.win.xs_music.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/singer")
public class SingerController {
    @Autowired
    private SingerService singerService;

    @GetMapping("/getSingCount")
    public R getSingCount() {
        int count = singerService.count();
        return R.success(count);
    }

    @GetMapping("/getSingerLocationCategory")
    public R getSingerLocationCategory(){
        return singerService.getSingerLocationCategory();
    }
}
