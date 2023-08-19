package com.win.xs_music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.win.xs_music.pojo.Collect;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CollectMapper extends BaseMapper<Collect> {

    /**
     * 通过登录用户id查询用户的收藏的歌手id
     *
     * @param id
     * @return
     */
    @Select("select singer_id from collect where user_id = #{id} and type = 2")
    List<Integer> getCollectSingerByUserId(Integer id);

    /**
     * 获取当前登录用户所收藏的歌单
     *
     * @param id
     * @return
     */
    @Select("select song_list_id from collect where user_id = #{id} and type = 1")
    List<Integer> getCollectSongListByUserId(Integer id);


    @Select("select song_id from collect where user_id = #{userId} and type = 0")
    List<Integer> geMyLoveSongIdsByUserId(Integer userId);

    @Delete("delete from collect where user_id = #{userId} and song_id = #{id} and type = 0")
    boolean deleteWithUserIdAndSongId(Integer userId, Integer id);

    @Delete("delete from collect where user_id = #{userId} and song_list_id = #{id} and type = 1")
    boolean deleteMyCollectSongListWithUserIdAndSongListId(Integer userId, Integer id);

    @Select("select * from collect where user_id = #{userId} and song_id = #{id} and type = 0")
    Collect getMyLoveSongWithUserIdAndSongId(Integer userId, Integer id);
}
