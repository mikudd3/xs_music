package com.win.xs_music.mapper.view;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.win.xs_music.pojo.Singer;
import com.win.xs_music.view.SongView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SongViewMapper extends BaseMapper<SongView> {

    @Select("select singerName from songView")
    List<String> getSingerName();


}
