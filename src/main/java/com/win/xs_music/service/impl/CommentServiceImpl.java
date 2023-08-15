package com.win.xs_music.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.win.xs_music.mapper.CommentMapper;
import com.win.xs_music.pojo.Comment;
import com.win.xs_music.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
}
