package com.win.xs_music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.win.xs_music.common.R;
import com.win.xs_music.pojo.ListSong;


public interface ListSongService extends IService<ListSong> {


    R add(Integer songId, Integer songListId);
}
