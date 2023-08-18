package com.win.xs_music.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.win.xs_music.common.R;
import com.win.xs_music.mapper.CommentMapper;
import com.win.xs_music.pojo.Comment;
import com.win.xs_music.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    CommentMapper commentMapper;


    @Override
    public R getCommentList(byte type) {
        List<Comment> liss = commentMapper.getCommentList(type);
        return R.success(liss);
    }

}
