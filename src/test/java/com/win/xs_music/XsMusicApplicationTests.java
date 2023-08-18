package com.win.xs_music;

import com.win.xs_music.mapper.SongMapper;
import com.win.xs_music.pojo.Song;
import com.win.xs_music.service.SingerService;
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
    private SongMapper songMapper;
    @Test
    void contextLoads() {
        singerService.getSingerLocationCategory();
    }

    @Test
    void getList(){
        List<Song> songs = songMapper.selectList1(6);
        log.info("查询到的信息为：{}",songs.size());
    }

}
