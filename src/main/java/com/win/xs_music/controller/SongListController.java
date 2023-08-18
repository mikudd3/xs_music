package com.win.xs_music.controller;


import com.win.xs_music.common.R;
import com.win.xs_music.dto.SongListPageDto;
import com.win.xs_music.pojo.SongList;
import com.win.xs_music.service.SongListService;
import com.win.xs_music.vo.SongListflVo;
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
        int count = songListService.count();
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
        return songListService.getPage(songListPageDto.getCurrentPage(), songListPageDto.getPageSize(), songListPageDto.getName());
    }

    /**
     * 获取歌单分类的数量
     *
     * @return
     */
    @GetMapping("/getStyle")
    public R getStyle() {
        R result = songListService.getStyle();
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
        boolean ret = songListService.updateById(songList);
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
        boolean b = songListService.removeById(id);
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
        ArrayList<SongListflVo> sl = songListService.getSongList(style_name);
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
        return  songListService.getIndexSongList();
    }
}
