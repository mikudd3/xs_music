package com.win.xs_music.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.win.xs_music.common.R;
import com.win.xs_music.view.SongView;

public interface SongViewService extends IService<SongView> {



    //歌曲分页查询
    R getPage(Integer currentPage, Integer pageSize, String singerName);

    //添加歌曲
    R add(SongView songView);
}
