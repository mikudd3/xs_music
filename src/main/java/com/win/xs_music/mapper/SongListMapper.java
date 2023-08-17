package com.win.xs_music.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.win.xs_music.pojo.SongList;
import com.win.xs_music.vo.SongListflVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper

public interface SongListMapper extends BaseMapper<SongList> {


    List<Map<String, Object>> getStyle();
    ArrayList<SongListflVo> getSongList(String style_name);
}
