package com.win.xs_music.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project:
 * @author: cbj
 * @version: 1.0
 */
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongListflPageDto implements Serializable {
    private String style_name;
}
