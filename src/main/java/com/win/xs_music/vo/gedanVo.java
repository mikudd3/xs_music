package com.win.xs_music.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class gedanVo {
    //歌单标题
    private String title;
    //歌单介绍
    private String  introduction;
    //歌单图片
    private String pic;
}
