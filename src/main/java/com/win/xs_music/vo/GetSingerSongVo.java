package com.win.xs_music.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @project:
 * @author: mikudd3
 * @version: 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSingerSongVo implements Serializable {

    private Integer id;
    private Integer singerId;
    private String name;
    private String introduction;
    private String pic;
    private String lyric;
    private String url;
    //是否是我喜欢的歌曲
    private Boolean like;
}
