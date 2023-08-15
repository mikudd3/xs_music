package com.win.xs_music.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@TableName(value = "singer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Singer implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Byte sex;
    private String pic;
    private Date birth;
    private String location;
    private String introduction;
    private Long fans; //粉丝数量
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime; //更新时间
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime; //创建时间


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
