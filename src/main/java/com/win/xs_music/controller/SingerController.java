package com.win.xs_music.controller;


import com.win.xs_music.common.R;
import com.win.xs_music.dto.SingerPageDto;
import com.win.xs_music.pojo.Singer;
import com.win.xs_music.service.SingerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.Location;

@RestController
@RequestMapping("/singer")
@Slf4j
public class SingerController {
    @Autowired
    private SingerService singerService;


    //查询歌手男女个数
    @GetMapping("/getSingCount")
    public R getSingCount() {
        return singerService.selectCount();
    }

    //获取歌手分布地区信息以及对应的数量
    @GetMapping("/getSingerLocationCategory")
    public R getSingerLocationCategory() {
        R r = singerService.getSingerLocationCategory();
        log.info(String.valueOf(r));
        return r;
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

    //查询一个歌手信息
    @PostMapping("one")
    public R getOne(Integer id){
        log.info("查询的歌手id为：{}",id);
        return singerService.getOne(id);
    }

    @PostMapping("/getSingers")
    public R getSingersFromInformation(Singer singer) {
        log.info("传入的歌手信息" + singer);
        return singerService.getSingers(singer);
    }

}
