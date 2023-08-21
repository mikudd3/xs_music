package com.win.xs_music.controller;

import com.win.xs_music.common.CustomException;
import com.win.xs_music.common.R;
import com.win.xs_music.service.StyleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @project:
 * @author: fqq
 * @version: 1.0
 */
@RestController
@RequestMapping("/style")
@Slf4j
public class StyleController {

    @Autowired
    private StyleService styleService;

    /**
     * 获取分类名
     * @return
     */
    @PostMapping("/getStyleName")
    public R getStyleName(){
        try {
            return  styleService.getStyleName();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }
}
