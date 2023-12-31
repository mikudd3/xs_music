package com.win.xs_music.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @project:
 * @author: fqq
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongListVo {
    //存放歌曲详细信息和歌手名
    private Integer id;

    private Integer singerId;

    private String name;

    private String introduction;

    private String pic;

    private String lyric;

    private String url;

    private LocalDateTime updateTime; //更新时间
    private LocalDateTime createTime; //创建时间

    private String singerName;



}
