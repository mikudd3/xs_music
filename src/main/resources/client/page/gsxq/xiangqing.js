new Vue({
    el: "#app",
    data() {
        return {
            singer: {
                name: "许嵩",              //歌手名字
                introduction: "著名作词人、作曲人、唱片制作人、歌手。内地独立音乐之标杆人物，有音乐鬼才之称。2009年独立出版首张词曲全创作专辑《自定义》，2010年独立出版第二张词曲全创作专辑《寻雾启示》，两度掀起讨论热潮，一跃成为内地互联网讨论度最高的独立音乐人。2011年加盟海蝶音乐，推出第三张词曲全创作专辑《苏格拉没有底》，发行首月即在大陆地区摘下唱片销量榜冠军，轰动全国媒体，并拉开全国巡回签售及歌迷见面会。",
                sex: "男",                 //歌手性别
                birth: "2020-01-01",       //歌手生日
                location: "中国",          //歌手国籍
                pic: "https://bkimg.cdn.bcebos.com/pic/d1a20cf431adcbef76091cafaff839dda3cc7cd93159?x-bce-process=image/resize,m_lfit,w_536,limit_1/format,f_auto",                //歌手图片
                isCollected: false,
            },

            //歌曲列表
            songs: [
                {
                    id: "",
                    name: "11111",
                    introduction: "11111",
                }
            ],
            showPopup: false,
            songLists: [
                {
                    id: 1,
                    title: "",
                }
            ],
            //用于判断收藏是否成功
            sl: 0,
        }
    },
    mounted() {
        this.getAll();
    },
    methods: {
        //进入界面获取信息
        getAll() {
            const searchParams = new URLSearchParams(window.location.search);
            const id = searchParams.get('id');
            console.log(id)
            axios({
                method: "post",
                url: "/singer/one?id=" + id,
            }).then(res => {
                if (res.data.code == 1) {
                    this.singer = res.data.data;
                }
            });
            axios({
                method: "post",
                url: "/song/list?id=" + id,
            }).then(rest => {
                this.songs = rest.data.data;
            })
        },
        //点击歌曲输出歌曲id
        handleClick(song) {
            sessionStorage.setItem("songs", JSON.stringify(song));
            console.log(song)
        },
        // 收藏列表
        togglePopup() {
            this.showPopup = !this.showPopup;
            axios({
                url: "/songlist/getMyCreateSongList",
                method: "get",
            }).then(resp => {
                if (resp.data.code == 1) {
                    this.songLists = resp.data.data;
                }
            })
        },
        //将歌曲添加到用户创建的歌单
        getPriceRange(songListId, songId) {
            axios({
                url: "/listSong/add",
                method: "get",
                params: {
                    song_list_id: songListId,
                    song_id: songId
                }
            }).then(resp => {
                if (resp.data.code != 1) {
                    this.$message.error("添加失败");
                }
            })
        },

        //点击播放全部
        playAll() {
            console.log(this.songs)
            sessionStorage.setItem("songs", JSON.stringify(this.songs));
        },
        toggleCollect(itemId) {
            this.singer.isCollected = !this.singer.isCollected;
            if (!this.singer.isCollected) {
                // 执行收藏操作
                axios({
                    url: "/collect/collectSongList?id=" + itemId,
                    method: "post",
                }).then(resp => {
                    if (resp.data.code == 1) {
                        this.$message.error("收藏成功");
                        this.singer.isCollected = !this.singer.isCollected;
                    } else {
                        this.$message.error(resp.data.msg);
                    }
                })
            } else {
                // 执行取消收藏操作
                axios({
                    url: "/collect/deleteMyCollectSongList?id=" + itemId,
                    method: "post",
                }).then(resp => {
                    if (resp.data.code == 1) {
                        this.$message.error("取消成功");
                        this.singer.isCollected = !this.singer.isCollected;
                    } else {
                        this.$message.error(resp.data.msg);
                    }
                })
            }
        }

    }

})