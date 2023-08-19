new Vue({
    el: "#app",
    data: {
        songs: [
            {
                //必要信息
                id: 30,
                pic: 'http://www.170hi.com/wp-content/themes/beetube/images/nopic.png',
                url: 'https://www.ihaoge.net/server/1/287280938.mp3',
                name: '叮叮当',
                singerName: '宝宝巴士',
                like: true
            },
            {
                pic: 'https://star.kuwo.cn/star/starheads/180/21/12/1142472669.jpg',
                url: 'https://www.ihaoge.net/server/1/283424829.mp3',
                name: '水中花(Live)',
                singerName: '郁可唯',
                like: true
            },
            {
                pic: 'http://static.170hi.com/wp-content/themes/beetube/images/nopic.png',
                url: 'https://www.ihaoge.net/server/1/289548192.mp3',
                name: '僕らのスペクトラ',
                singerName: '北谷洋',
                like: false
            }
        ],
        songLists: [
            {
                id: 1,
                title: "11111",
            }
        ],
        showPopup: false,
    },
    mounted() {
        this.getSong();
    },
    methods: {
        getSong() {
            this.searchParams = new URLSearchParams(window.location.search);
            const songname = this.searchParams.get('songname');
            // alert(songname);
            axios({
                url: "/song/searchSong?songname=" + songname,
                method: "get",
            }).then(resp => {
                if (resp.data.code == 1) {
                    this.songs = resp.data.data;
                    this.$message.success("添加成功")
                } else {
                    this.$message.error(resp.data.msg)
                }
            })
        },
        //喜欢
        toggleFavorite(row) {
            if (row.like) {
                //添加到我喜欢
                axios({
                    url: "/collect/addMyLoveSong",
                    method: "post",
                    data: {
                        id: row.id
                    }
                }).then(resp => {
                    if (resp.data.code == 1) {
                        row.like = !row.like;
                        this.$message.success("添加成功")
                    } else {
                        this.$message.error(resp.data.msg)
                    }
                })
            } else {
                //取消我喜欢
                axios({
                    url: "/collect/deleteMyLoveSong",
                    method: "post",
                    data: {
                        id: row.id
                    }
                }).then(resp => {
                    if (resp.data.code == 1) {
                        row.like = !row.like;
                        this.$message.success("取消成功")
                    } else {
                        this.$message.error(resp.data.msg)
                    }
                })
            }
        },
        //播放
        addtoplay(row) {
            //信息塞进session域中
            sessionStorage.setItem("songs", JSON.stringify(row));
        },
        // 收藏列表
        togglePopup() {
            console.log(1111)
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
                url: "/songlist/add",
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
    }
});