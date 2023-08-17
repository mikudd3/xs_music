package com.win.xs_music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.win.xs_music.common.R;
import com.win.xs_music.pojo.Singer;
import com.win.xs_music.pojo.Song;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface SongMapper extends BaseMapper<Song> {



    @Select("select * from song where singer_id = #{id}")
    List<Song> getListBySingerId(Integer id);

    @Delete("delete from song where singer_id = #{id}")
    void deleteBySingerId(Integer id);


    @Select("select * from singer where id = #{id}")
    Singer selectOne(Integer id);
}
