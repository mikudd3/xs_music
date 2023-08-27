package com.win.xs_music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.win.xs_music.pojo.Singer;
import com.win.xs_music.pojo.Song;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SingerMapper extends BaseMapper<Singer> {

    List<Map<String, Object>> getSingerLocationCategory();
    List<Singer> getSingers(Singer singer);


    @Select("select * from singer where  name = #{singerName}")
    Singer selectByName(String singerName);

    @Select("select name from singer")
    List<String> getSingerName();






}
