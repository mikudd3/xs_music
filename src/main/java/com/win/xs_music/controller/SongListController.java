package com.win.xs_music.controller;


import com.win.xs_music.common.CustomException;
import com.win.xs_music.common.R;
import com.win.xs_music.dto.SongListPageDto;
import com.win.xs_music.pojo.SongList;
import com.win.xs_music.service.SongListService;
import com.win.xs_music.vo.SongListflVo;
import com.win.xs_music.vo.gedanVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/songlist")
@Slf4j
public class SongListController {

    @Autowired
    private SongListService songListService;

    /**
     * 获取歌单数量
     *
     * @return
     */
    @GetMapping("/getSongListCount")
    public R getSongListCount() {
        int count = 0;
        try {
            count = songListService.count();
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
        return R.success(count);
    }

    /**
     * 歌单信息分页查询
     *
     * @param songListPageDto
     * @return
     */
    @PostMapping("/page")
    public R page(@RequestBody SongListPageDto songListPageDto) {
        log.info("歌单信息分页查询:{}", songListPageDto);
        try {
            return songListService.getPage(songListPageDto.getCurrentPage(), songListPageDto.getPageSize(), songListPageDto.getName());
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    /**
     * 获取歌单分类的数量
     *
     * @return
     */
    @GetMapping("/getStyle")
    public R getStyle() {
        R result = null;
        try {
            result = songListService.getStyle();
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
        log.info(String.valueOf(result));
        return result;
    }

    /**
     * 更新歌单数据
     *
     * @param songList
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody SongList songList) {
        log.info("更新歌单传入信息：{}", songList);
        boolean ret = false;
        try {
            ret = songListService.updateById(songList);
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
        return ret ? R.success("更新成功") : R.error("更新失败");
    }

    /**
     * 删除歌单
     *
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public R delete(Integer id) {
        log.info("要删除的用户id为:{}", id);
        boolean b = false;
        try {
            b = songListService.removeById(id);
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
        return b ? R.success("删除成功") : R.success("删除失败");
    }

    /**
     * 歌单分类
     *
     * @param style_name
     * @return
     */
    @RequestMapping("/songfl")
    public R getSongList(String style_name) {
        ArrayList<SongListflVo> sl = null;
        try {
            sl = songListService.getSongList(style_name);
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
        log.info(String.valueOf(sl));
        return R.success(sl);
    }

    /**
     * 首页获取歌单数据
     *
     * @return
     */
    @GetMapping("/getSongList")
    public R getIndexSongList() {
        try {
            return songListService.getIndexSongList();
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
    }


    @PostMapping("/one")
    public R getOne(Integer id) {
        try {
            log.info("查询的歌单id为：{}", id);
            return songListService.getOne(id);
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
    }


    @GetMapping("/getMyCreateSongList")
    /*传入用户的id*/
    public R getMyCreateSongList(){
        try {
            return songListService.getMyCreateSongList();
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }
    }

}
