package com.win.xs_music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.win.xs_music.common.R;
import com.win.xs_music.pojo.SongList;
import com.win.xs_music.vo.SongListflVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public interface SongListService extends IService<SongList> {

    R getPage(Integer currentPage, Integer pageSize, String name);

    R getStyle();


    ArrayList<SongListflVo> getSongList(String style_name);

    /**
     * 首页获取歌单数据
     *
     * @return
     */
    R getIndexSongList();

    R getOne(Integer id);

    R getMyCreateSongList();
}
