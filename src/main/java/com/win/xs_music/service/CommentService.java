package com.win.xs_music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.win.xs_music.common.R;
import com.win.xs_music.pojo.Comment;


public interface CommentService extends IService<Comment> {
    /**
     * 获取评论
     * @param comment
     * @return
     */
    R getCommentList(Comment comment);

}
