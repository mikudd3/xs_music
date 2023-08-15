package com.win.xs_music.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@TableName(value = "song")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Song implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("singer_id")
    private Integer singerId;

    private String name;

    private String introduction;


    private String pic;

    private String lyric;

    private String url;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime; //更新时间
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime; //创建时间

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
