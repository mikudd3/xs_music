package com.win.xs_music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.win.xs_music.common.R;
import com.win.xs_music.mapper.SongMapper;
import com.win.xs_music.pojo.Song;
import com.win.xs_music.service.SongService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {

}
