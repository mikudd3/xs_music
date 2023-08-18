package com.win.xs_music.controller;


import com.win.xs_music.common.CustomException;
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
        int count = 0;
        try {
            count = songService.count();
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
        return R.success(count);
    }


    //获取歌手名
    @GetMapping("/getSingerName")
    public R getSingerName() {
        try {
            return songService.getSingerName();
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    //歌曲分页查询
    @PostMapping("/page")
    public R page(@RequestBody SongPageDto songPageDto) {
        log.info("歌曲分类查询：{}", songPageDto);
        try {
            return songViewService.getPage(songPageDto.getCurrentPage(), songPageDto.getPageSize(), songPageDto.getSingerName());
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    //添加歌曲
    @PostMapping("/add")
    public R addSong(@RequestBody SongView songView) {
        log.info("添加歌曲接收信息：{}", songView);
        try {
            return songViewService.add(songView);
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    //更新歌曲接收信息
    @PostMapping("/update")
    public R updateSong(@RequestBody SongView songView) {
        log.info("更新歌曲接收的信息:{}", songView);
        try {
            return songService.updateSong(songView);
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    //删除歌曲
    @PostMapping("/delete")
    public R delete(Integer id) {
        boolean ret = songService.removeById(id);
        try {
            return ret ? R.success("删除成功") : R.error("删除失败");
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
    }


    //查询歌手的歌曲列表
    @PostMapping("list")
    public R selectList(Integer id) {
        try {
            return songService.selectList(id);
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    //查询歌单的歌曲列表
    @PostMapping("list2")
    public R selectList1(Integer id){
        return songService.selectList1(id);
    }



}
