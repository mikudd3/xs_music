package com.win.xs_music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.win.xs_music.common.R;
import com.win.xs_music.pojo.SongList;
import org.springframework.web.multipart.MultipartFile;

public interface SongListService extends IService<SongList> {

    R getStyle();
}
