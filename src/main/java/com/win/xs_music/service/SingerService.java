package com.win.xs_music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.win.xs_music.common.R;
import com.win.xs_music.pojo.Singer;
import org.springframework.web.multipart.MultipartFile;

public interface SingerService extends IService<Singer> {

    /**
     * 获取歌手分类
     *
     * @return
     */
    R getSingerLocationCategory();

    /**
     * 歌手信息分页查询
     *
     * @param currentPage
     * @param pageSize
     * @param name
     * @return
     */
    R getPage(Integer currentPage, Integer pageSize, String name);

    /**
     * 删除歌手
     *
     * @param id
     * @return
     */
    R delete(Integer id);

    /**
     * 获取歌手总数
     *
     * @return
     */
    R selectCount();

    /**
     * 获取单个歌手信息
     *
     * @param id
     * @return
     */
    R getOne(Integer id);

    /**
     * 获取全部歌手
     *
     * @param singer
     * @return
     */
    R getSingers(Singer singer);

    /**
     * 添加歌手
     *
     * @param singer
     * @return
     */
    R add(Singer singer);

    /**
     * 更新歌手信息
     *
     * @param singer
     * @return
     */
    R updateSinger(Singer singer);
}
