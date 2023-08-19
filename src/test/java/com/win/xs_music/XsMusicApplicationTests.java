package com.win.xs_music;

import com.win.xs_music.common.R;
import com.win.xs_music.mapper.SongMapper;
import com.win.xs_music.pojo.SongList;
import com.win.xs_music.service.SingerService;
import com.win.xs_music.service.SongListService;
import com.win.xs_music.service.StyleService;
import com.win.xs_music.vo.SongListVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class XsMusicApplicationTests {

    @Autowired
    private SingerService singerService;
    @Autowired
    private SongListService songListService;

    @Autowired
    private StyleService styleService;
    @Autowired
    private SongMapper songMapper;
    @Test
    void contextLoads() {
        singerService.getSingerLocationCategory();
    }

    @Test
    void getList(){
        List<SongListVo> songs = songMapper.selectList1(6);
        log.info("查询到的信息为：{}",songs.size());
    }

    @Test
    void getMyCreateSongList(){
        R myCreateSongList = songListService.getMyCreateSongList();
        System.out.println(myCreateSongList.getData());
    }

    @Test
    void test1(){
        songListService.getSongList("日韩");
    }


    @Test
    void test2(){
        styleService.getStyleName();
    }
}
