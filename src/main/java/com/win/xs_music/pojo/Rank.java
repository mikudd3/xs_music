package com.win.xs_music.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName(value = "rank")
@Data
public class Rank implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("song_list_id")
    private Long songListId;

    @TableField("user_id")
    private Long userId;
    private Double score;

}
