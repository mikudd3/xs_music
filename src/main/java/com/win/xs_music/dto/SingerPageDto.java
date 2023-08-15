package com.win.xs_music.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @project:
 * @author: mikudd3
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingerPageDto implements Serializable {
    private Integer currentPage;
    private Integer pageSize;
    //模糊查询条件
    private String name;
}
