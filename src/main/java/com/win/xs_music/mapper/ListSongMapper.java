package com.win.xs_music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.win.xs_music.pojo.ListSong;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ListSongMapper extends BaseMapper<ListSong> {

    @Select("select * from list_song where song_id = #{songId} and song_list_id = #{songListId}")
    ListSong selectWithSongIdAndSongListId(Integer songId, Integer songListId);
}
