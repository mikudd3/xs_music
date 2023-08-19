package com.win.xs_music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.win.xs_music.common.R;
import com.win.xs_music.pojo.Song;
import com.win.xs_music.view.SongView;
import org.springframework.web.multipart.MultipartFile;

public interface SongService extends IService<Song> {


    R updateSong(SongView songView);

    R getSingerName();

    R selectList(Integer id);

    R selectList1(Integer id);

    //搜索歌曲
    R getSongByName(String songname);

    //获取当前正在播放的音乐的信息
    R getSong(Integer id);
}
