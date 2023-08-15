package com.win.xs_music.dto;

import com.win.xs_music.pojo.Admin;
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
public class AdminPageDto implements Serializable {
    private Integer currentPage;
    private Integer pageSize;
    private Admin admin;

}
