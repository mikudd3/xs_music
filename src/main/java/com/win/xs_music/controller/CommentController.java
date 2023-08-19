package com.win.xs_music.controller;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.win.xs_music.common.BaseContext;
import com.win.xs_music.common.CustomException;
import com.win.xs_music.common.R;
import com.win.xs_music.pojo.Admin;
import com.win.xs_music.pojo.Comment;
import com.win.xs_music.service.AdminService;
import com.win.xs_music.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/gets")
    public R getComments(Comment comment) {
        R comment_list;
        log.info("!!!!!!!!!!!!!!!!!!!!!!");
        log.info("查询歌单评论数" + comment);
        if (comment.getSongListId() == null) {
            return R.error("null");
        }
        try {
            comment_list = commentService.getCommentList(comment);
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
        return comment_list;
    }

    @PostMapping("/add")
    public R addComment(Comment comment) {
        log.info("添加评论所输入信息：{}", comment);

        boolean save = false;
        try {
            save = commentService.save(comment);
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
        return save ? R.success("添加成功") : R.success("添加失败");
    }
}
