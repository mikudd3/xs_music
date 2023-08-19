package com.win.xs_music.controller;


import com.win.xs_music.common.CustomException;
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
        try {
            return singerService.selectCount();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    //获取歌手分布地区信息以及对应的数量
    @GetMapping("/getSingerLocationCategory")
    public R getSingerLocationCategory() {
        try {
            return singerService.getSingerLocationCategory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    //歌手信息分页查询
    @PostMapping("/page")
    public R page(@RequestBody SingerPageDto singerPageDto) {
        log.info("歌手信息分页查询:{}", singerPageDto);
        try {
            return singerService.getPage(singerPageDto.getCurrentPage(), singerPageDto.getPageSize(), singerPageDto.getName());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }


    //添加歌手
    @PostMapping("/add")
    public R add(@RequestBody Singer singer) {
        log.info("添加歌手传入信息：{}", singer);
        boolean ret = false;
        try {
            ret = singerService.save(singer);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
        return ret ? R.success("添加成功") : R.error("添加失败");
    }


    //更新数据
    @PostMapping("/update")
    public R update(@RequestBody Singer singer) {
        log.info("更新歌手传入信息：{}", singer);
        boolean ret = false;
        try {
            ret = singerService.updateById(singer);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
        return ret ? R.success("更新成功") : R.error("更新失败");
    }

    //删除歌手
    @PostMapping("/delete")
    public R delete(Integer id) {
        log.info("删除歌手传入信息：{}", id);
        try {
            return singerService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    //查询一个歌手信息
    @PostMapping("one")
    public R getOne(Integer id){
        log.info("查询的歌手id为：{}",id);
        try {
            return singerService.getOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    @GetMapping("/getSingers")
    public R getSingersFromInformation(Singer singer) {
        log.info("传入的歌手信息" + singer);
        log.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        try {
            return singerService.getSingers(singer);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }

}
