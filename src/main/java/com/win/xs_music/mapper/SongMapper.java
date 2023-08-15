package com.win.xs_music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.win.xs_music.pojo.Song;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface SongMapper extends BaseMapper<Song> {

}
