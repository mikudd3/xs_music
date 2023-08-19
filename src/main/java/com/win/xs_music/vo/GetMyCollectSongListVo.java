package com.win.xs_music.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @project:
 * @author: mikudd3
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMyCollectSongListVo implements Serializable {
    private Integer id;
    private String title;
    private String pic;
    private String introduction;
    private Long love;
    private String styleIds;//风格
    //false表示没有收藏，true表示已经被收藏
    private Boolean isCollected;
}
