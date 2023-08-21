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

    /**
     * 获取评论
     *
     * @param comment
     * @return
     */
    @GetMapping("/gets")
    public R getComments(Comment comment) {
        log.info("查询歌单评论数" + comment);
        try {
            if (comment.getSongListId() == null) {
                return R.error("null");
            }
            return commentService.getCommentList(comment);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    /**
     * 添加评论
     *
     * @param comment
     * @return
     */
    @PostMapping("/add")
    public R addComment(Comment comment) {
        log.info("添加评论所输入信息：{}", comment);
        try {
            boolean save = commentService.save(comment);
            return save ? R.success("添加成功") : R.success("添加失败");
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }

    }
}
