package com.win.xs_music.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

@TableName(value = "list_song")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListSong implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("song_id")
    private Integer songId;
    @TableField("song_list_id")
    private Integer songListId;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
