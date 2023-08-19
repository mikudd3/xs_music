package com.win.xs_music.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

@TableName(value = "song_list")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongList implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String pic;
    private String introduction;
    private Integer user_id;
    private Long love;
    @TableField("style_ids")
    private String styleIds;//风格
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime; //创建时间

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
