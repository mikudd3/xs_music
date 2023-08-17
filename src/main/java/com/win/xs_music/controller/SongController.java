package com.win.xs_music.controller;


import com.win.xs_music.common.R;
import com.win.xs_music.dto.SongPageDto;
import com.win.xs_music.service.SongService;
import com.win.xs_music.service.SongViewService;
import com.win.xs_music.view.SongView;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/song")
@Slf4j
public class SongController {

    @Autowired
    private SongService songService;
    @Autowired
    private SongViewService songViewService;

    //查找歌曲数量
    @GetMapping("/getSongCount")
    public R getSongCount() {
        int count = songService.count();
        return R.success(count);
    }


    //获取歌手名
    @GetMapping("/getSingerName")
    public R getSingerName() {
        return songService.getSingerName();
    }

    //歌曲分页查询
    @PostMapping("/page")
    public R page(@RequestBody SongPageDto songPageDto) {
        log.info("歌曲分类查询：{}", songPageDto);
        return songViewService.getPage(songPageDto.getCurrentPage(), songPageDto.getPageSize(), songPageDto.getSingerName());
    }

    //添加歌曲
    @PostMapping("/add")
    public R addSong(@RequestBody SongView songView) {
        log.info("添加歌曲接收信息：{}", songView);
        return songViewService.add(songView);
    }

    //更新歌曲接收信息
    @PostMapping("/update")
    public R updateSong(@RequestBody SongView songView) {
        log.info("更新歌曲接收的信息:{}", songView);
        return songService.updateSong(songView);
    }

    //删除歌曲
    @PostMapping("/delete")
    public R delete(Integer id) {
        boolean ret = songService.removeById(id);
        return ret ? R.success("删除成功") : R.error("删除失败");
    }

    @PostMapping("list")
    public R selectList(Integer id){
      return  songService.selectList(id);
    }


}
