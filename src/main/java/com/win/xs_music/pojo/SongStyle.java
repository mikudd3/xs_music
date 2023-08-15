package com.win.xs_music.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @project:
 * @author: mikudd3
 * @version: 1.0
 */

@TableName("song_style")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongStyle implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("style_name")
    private String styleName;

}
