package com.win.xs_music.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project:
 * @author: cbj
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongListflVo {
    //歌单id
    private Integer id;
    //图片
    private String pic;
    //介绍
    private String title;
    //收藏
    private Integer love;
}
