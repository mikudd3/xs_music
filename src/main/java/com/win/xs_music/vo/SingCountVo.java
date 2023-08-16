package com.win.xs_music.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project:
 * @author: fqq
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingCountVo {

    //男歌手数量
    private Integer singerCountMan;
    //女歌手数量
    private Integer singerCountWomen;
    //歌手总数量
    private Integer singerCount;

}
