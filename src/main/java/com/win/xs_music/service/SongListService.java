package com.win.xs_music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.win.xs_music.common.R;
import com.win.xs_music.pojo.SongList;
import org.springframework.web.multipart.MultipartFile;

public interface SongListService extends IService<SongList> {

    R getPage(Integer currentPage, Integer pageSize, String name);

    R getStyle();

    //删除歌单
//    R delete(Integer id);
}
