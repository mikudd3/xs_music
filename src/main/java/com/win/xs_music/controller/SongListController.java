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
        try {
            int count = songListService.count();
            return R.success(count);
        } catch (Exception e) {
            throw new CustomException("系统错误，请联系管理员");
        }

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
        try {
            return songListService.getStyle();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    /**
     * 更新歌单数据
     *
     * @param songList
     * @return
     */
    @PostMapping("/update")
    public R update(@RequestBody SongList songList) {
        try {
            log.info("更新歌单传入信息：{}", songList);
            boolean ret = songListService.updateById(songList);
            return ret ? R.success("更新成功") : R.error("更新失败");
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }

    }

    /**
     * 删除歌单
     *
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public R delete(Integer id) {
        try {
            log.info("要删除的用户id为:{}", id);
            boolean b = songListService.removeById(id);
            return b ? R.success("删除成功") : R.success("删除失败");
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }

    }

    /**
     * 歌单分类
     *
     * @param style_name
     * @return
     */
    @RequestMapping("/songfl")
    public R getSongList(String style_name) {
        try {
            ArrayList<SongListflVo> sl = songListService.getSongList(style_name);
            log.info(String.valueOf(sl));
            return R.success(sl);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }

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
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    /**
     * 根据id获取歌单
     *
     * @param id
     * @return
     */
    @PostMapping("/one")
    public R getOne(Integer id) {
        try {
            log.info("查询的歌单id为：{}", id);
            return songListService.getOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }

    /**
     * 获取我创建的歌单列表
     *
     * @return
     */
    @GetMapping("/getMyCreateSongList")
    /*传入用户的id*/
    public R getMyCreateSongList() {
        try {
            return songListService.getMyCreateSongList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }


    /**
     * 新建歌单
     *
     * @param songList
     * @return
     */
    @PostMapping("/addSongList")
    public R addSongList(@RequestBody SongList songList) {
        log.info("输入的数据为：{}", songList);
        try {
            return songListService.addSongList(songList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("系统错误，请联系管理员");
        }
    }


}
