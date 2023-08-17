package com.win.xs_music.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

@TableName(value = "collect")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Collect implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("user_id")
    private Integer userId; //用户id
    private Byte type; //类型（0歌曲1歌单2歌手）
    @TableField("song_id")
    private Integer songId; //歌id
    @TableField("song_list_id")
    private Integer songListId; //歌单id
    @TableField("singer_id")
    private Integer singerId;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime; //创建时间

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
