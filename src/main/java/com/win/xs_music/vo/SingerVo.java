package com.win.xs_music.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @project:
 * @author: fqq
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingerVo {
    //返回歌手详情的数据
    private Integer id;          //歌手id
    private String name;        //歌手姓名
    private Byte sex;           //性别
    private String location;    //歌手国籍
    private String pic;         //歌手图片地址
    private String introduction; //歌手简介
    private Date birth;          //歌手生日
    private Long fans;          //粉丝数量
    private Integer sid;        //歌曲id
    private String sname;       //歌名
    private String zhuanji;       //专辑
    private String spic;       //歌曲图片

}
