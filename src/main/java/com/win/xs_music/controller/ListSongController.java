package com.win.xs_music.controller;


import com.win.xs_music.common.CustomException;
import com.win.xs_music.common.R;
import com.win.xs_music.service.ListSongService;
import com.win.xs_music.service.SongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/listSong")
public class ListSongController {

    @Autowired
    private ListSongService listSongService;

    //把歌曲添加到用户创建的歌单
    @RequestMapping("/add")
    public R add(Integer song_id, Integer song_list_id) {
        try {
            return listSongService.add(song_id, song_list_id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }
}
