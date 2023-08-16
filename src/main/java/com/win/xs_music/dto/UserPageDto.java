package com.win.xs_music.dto;

import com.win.xs_music.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @project:
 * @author: fqq
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPageDto {

    private Integer currentPage;
    private Integer pageSize;
    //模糊查询
    private User user;
}
