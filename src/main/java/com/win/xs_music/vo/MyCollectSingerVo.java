package com.win.xs_music.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @project:
 * @author: mikudd3
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyCollectSingerVo implements Serializable {

    private Integer id;
    private String name;
    private Byte sex;
    private String pic;
    private LocalDate birth;
    private String location;
    private String introduction;
    private Long fans; //粉丝数量
    private LocalDateTime updateTime; //更新时间
    private LocalDateTime createTime; //创建时间
    //是否被收藏
    private Boolean isCollected;
}
