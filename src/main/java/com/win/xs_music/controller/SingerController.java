package com.win.xs_music.controller;


import com.win.xs_music.common.R;
import com.win.xs_music.dto.SingerPageDto;
import com.win.xs_music.pojo.Singer;
import com.win.xs_music.service.SingerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/singer")
@Slf4j
public class SingerController {
    @Autowired
    private SingerService singerService;

    @GetMapping("/getSingCount")
    public R getSingCount() {
        int count = singerService.count();
        return R.success(count);
    }

    //获取歌手分布地区信息以及对应的数量
    @GetMapping("/getSingerLocationCategory")
    public R getSingerLocationCategory() {
        return singerService.getSingerLocationCategory();

    }

    //歌手信息分页查询
    @PostMapping("/page")
    public R page(@RequestBody SingerPageDto singerPageDto) {
        log.info("歌手信息分页查询:{}", singerPageDto);
        return singerService.getPage(singerPageDto.getCurrentPage(), singerPageDto.getPageSize(), singerPageDto.getName());
    }


    //添加歌手
    @PostMapping("/add")
    public R add(@RequestBody Singer singer) {
        log.info("添加歌手传入信息：{}", singer);
        boolean ret = singerService.save(singer);
        return ret ? R.success("添加成功") : R.error("添加失败");
    }


    //更新数据
    @PostMapping("/update")
    public R update(@RequestBody Singer singer) {
        log.info("更新歌手传入信息：{}", singer);
        boolean ret = singerService.updateById(singer);
        return ret ? R.success("更新成功") : R.error("更新失败");
    }

    //删除歌手
    @PostMapping("/delete")
    public R delete(Integer id) {
        log.info("删除歌手传入信息：{}", id);
        return singerService.delete(id);
    }

}
