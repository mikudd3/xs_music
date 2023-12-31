package com.win.xs_music.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.win.xs_music.common.CustomException;
import com.win.xs_music.common.R;
import com.win.xs_music.mapper.CommentMapper;
import com.win.xs_music.pojo.Comment;
import com.win.xs_music.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    CommentMapper commentMapper;

    /**
     * 获取评论
     *
     * @param comment
     * @return
     */
    @Override
    public R getCommentList(Comment comment) {
        try {
            List<Map<String, Object>> liss = commentMapper.getCommentList(comment);
            return R.success(liss);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }

    }

}
