package com.win.xs_music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.win.xs_music.common.R;
import com.win.xs_music.pojo.Singer;
import com.win.xs_music.pojo.Song;
import com.win.xs_music.vo.SongListVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface SongMapper extends BaseMapper<Song> {



    @Select("select * from song.html where singer_id = #{id}")
    List<Song> getListBySingerId(Integer id);

    @Delete("delete from song.html where singer_id = #{id}")
    void deleteBySingerId(Integer id);


    @Select("select * from singer where id = #{id}")
    Singer selectOne(Integer id);


    @Select("SELECT * FROM song WHERE singer_id = #{id}")
    List<Song> selectList(Integer id);

    //多表查询查询指定歌单的歌曲
    List<SongListVo> selectList1(Integer id);
}
