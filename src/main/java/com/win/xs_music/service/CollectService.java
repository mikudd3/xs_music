package com.win.xs_music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.win.xs_music.common.R;
import com.win.xs_music.pojo.Collect;

public interface CollectService extends IService<Collect> {

    /**
     * 获取当前登录用户所关注的歌手
     *
     * @return
     */
    R getCollectSinger();

    /**
     * 获取当前登录用户所收藏的歌单
     *
     * @return
     */
    R getCollectSongList();

    /**
     * 获取我喜欢的音乐
     *
     * @return
     */
    R getMyLoveSong();

    //添加到我喜欢
    R addMyLoveSong(Integer id);

    //取消添加到我的喜欢
    R deleteMyLoveSong(Integer id);
}
