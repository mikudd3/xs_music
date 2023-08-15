package com.win.xs_music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.win.xs_music.pojo.Song;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface SongMapper extends BaseMapper<Song> {

    @Select("select * from song where singer_id = #{id}")
    List<Song> getListBySingerId(Integer id);

}
