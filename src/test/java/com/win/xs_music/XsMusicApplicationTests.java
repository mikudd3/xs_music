package com.win.xs_music;

import com.win.xs_music.service.SingerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class XsMusicApplicationTests {

    @Autowired
    private SingerService singerService;
    @Test
    void contextLoads() {
        singerService.getSingerLocationCategory();
    }

}
