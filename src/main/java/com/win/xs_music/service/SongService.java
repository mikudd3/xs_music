package com.win.xs_music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.win.xs_music.common.R;
import com.win.xs_music.pojo.Song;
import com.win.xs_music.view.SongView;
import org.springframework.web.multipart.MultipartFile;

public interface SongService extends IService<Song> {

    /**
     * 更新歌曲信息
     *
     * @param songView
     * @return
     */
    R updateSong(SongView songView);

    /**
     * 获取歌手名字
     *
     * @return
     */
    R getSingerName();

    /**
     * 查询歌手的歌曲列表
     *
     * @param id
     * @return
     */
    R selectList(Integer id);

    /**
     * 查询歌单的歌曲列表
     *
     * @param id
     * @return
     */
    R selectList1(Integer id);

    /**
     * 搜索歌曲
     *
     * @param songname
     * @return
     */
    R getSongByName(String songname);

    /**
     * 获取当前正在播放的音乐的信息
     *
     * @param id
     * @return
     */
    R getSong(Integer id);
}
