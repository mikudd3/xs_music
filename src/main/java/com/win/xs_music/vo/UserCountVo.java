package com.win.xs_music.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project:
 * @author: fqq
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCountVo {
    //男用户数量
    private Integer menCount;
    //女用户数量
    private Integer womenCount;
    //用户总数量
    private Integer userCount;
}
