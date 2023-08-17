package com.win.xs_music.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @project:
 * @author: fqq
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto implements Serializable {
    private String phone;
    private Integer code;

}
