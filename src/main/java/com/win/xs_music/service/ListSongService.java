package com.win.xs_music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.win.xs_music.common.R;
import com.win.xs_music.pojo.ListSong;


public interface ListSongService extends IService<ListSong> {

    /**
     * 向歌单里面添加歌曲
     * @param songId
     * @param songListId
     * @return
     */
    R add(Integer songId, Integer songListId);
}
