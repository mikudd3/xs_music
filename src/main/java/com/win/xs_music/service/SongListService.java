package com.win.xs_music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.win.xs_music.common.R;
import com.win.xs_music.pojo.SongList;
import com.win.xs_music.vo.SongListflVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public interface SongListService extends IService<SongList> {

    /**
     * 歌单分页查询
     * @param currentPage
     * @param pageSize
     * @param name
     * @return
     */
    R getPage(Integer currentPage, Integer pageSize, String name);

    /**
     * 获取歌单分类
     * @return
     */
    R getStyle();

    /**
     * 获取歌单列表
     * @param style_name
     * @return
     */
    ArrayList<SongListflVo> getSongList(String style_name);

    /**
     * 首页获取歌单数据
     *
     * @return
     */
    R getIndexSongList();

    /**
     * 根据id获取单个歌单信息
     * @param id
     * @return
     */
    R getOne(Integer id);

    /**
     * 获取我创建的歌单
     * @return
     */
    R getMyCreateSongList();

    /**
     * 创建歌单
     * @param songList
     * @return
     */
    R addSongList(SongList songList);
}
