package com.win.xs_music.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@TableName(value = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("user_id")
    private Integer userId;
    @TableField("song_id")
    private Integer songId;
    @TableField("song_list_id")
    private Integer songListId;
    private String content;
    private Byte type; //评论属于哪里（0歌曲，1歌单)
    private Integer up; //点赞数
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime; //更新时间
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime; //创建时间

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
