package com.win.xs_music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.win.xs_music.common.R;
import com.win.xs_music.pojo.Singer;
import org.springframework.web.multipart.MultipartFile;

public interface SingerService extends IService<Singer> {

    R getSingerLocationCategory();

    //歌手信息分页查询
    R getPage(Integer currentPage, Integer pageSize, String name);

    //删除歌手
    R delete(Integer id);

    R selectCount();
}
