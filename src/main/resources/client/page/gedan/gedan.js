new Vue({
    el: "#app",
    data() {
        return {
            //tang
            content: '',
            userData: [{}],
            comments: [{
                name: '妖怪',
                create_time: "2010-10-1",
                content: "show time!",
            }
            ],
            //歌单信息
            items: {

                title: "希望十八岁你爱的人是八十岁在你身边的人",   //歌单专题
                introduction: "",                 //歌单介绍
                pic: "",
                isCollected: false,
            },
            searchParams: "",
            //歌曲
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
            ],
            //当前登录用户的歌单
            songLists: [{
                title: "111",
            }],
            showPopup: false,
        }
    },
    mounted() {
        this.getAll();
        this.getUser();
        this.getAllComments();
    },
    methods: {

        getUser() {
            axios({
                url: "/user/getUser",
                method: "get",
            }).then(resp => {
                if (resp.data.code == 1) {
                    this.userData = resp.data.data;
                } else {
                    this.$message.error(resp.data.msg);
                }
            })
        },
        //获取全部评论
        getAllComments() {
            this.searchParams = new URLSearchParams(window.location.search);
            const id = this.searchParams.get('id');
            axios({
                method: "get",
                url: "/comment/gets",
                params: {
                    type: 1,
                    songListId: id,
                }
            }).then(res => {
                if (res.data.code) {
                    this.comments = res.data.data;
                    for (let i = 0; i < this.comments.length; i++) {
                        this.comments[i].tx = `/common/download?name=` + this.comments[i].tx;
                    }
                } else {
                    this.$message.error("res.data.msg");
                }
            })
        },
        setComment() {
            this.searchParams = new URLSearchParams(window.location.search);
            const id = this.searchParams.get('id');
            {
                axios({
                    method: "post",
                    url: "/comment/add",
                    params: {
                        userId: this.userData.id,
                        type: 1,
                        content: this.content,
                        songListId: id,
                    }
                }).then(ress => {
                    if (ress.data.code == 1) {
                        this.comments = ress.data.data;
                        this.content = '';
                        this.$message.success("评论成功");
                        this.getAllComments();
                    } else {
                        this.$message.error(ress.data.msg);
                    }
                })
            }
        },
        //播放全部
        playAll() {
            console.log(this.songs)
            sessionStorage.setItem("songs", JSON.stringify(this.songs));
        },
        //获取当前歌单的全部歌曲
        getAll() {
            this.searchParams = new URLSearchParams(window.location.search);
            const id = this.searchParams.get('id');
            //获取歌单的信息请求
            axios({
                method: "post",
                url: "/songlist/one?id=" + id,
            }).then(res => {
                this.items = res.data.data;
                this.items.pic = `/common/download?name=` + this.items.pic;
            });
            //获取歌单的歌曲请求
            axios({
                method: "post",
                url: "/song/list2?id=" + id,
            }).then(rest => {
                this.songs = rest.data.data;
            })
        },
        //点击歌曲输出歌曲
        handleClick(song) {
            sessionStorage.setItem("songs", JSON.stringify(song));
            console.log(song)
        },
        // 当前用户的收藏列表
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
        //收藏歌单
        toggleCollect(itemId) {
            if (!this.items.isCollected) {
                // 执行收藏操作
                axios({
                    url: "/collect/collectSongList?id=" + itemId,
                    method: "post",
                }).then(resp => {
                    if (resp.data.code == 1) {
                        this.$message.error("收藏成功");
                        this.items.isCollected = !this.items.isCollected;
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
                        this.items.isCollected = !this.items.isCollected;
                    } else {
                        this.$message.error(resp.data.msg);
                    }
                })
            }
        },
        //喜欢
        toggleFavorite(row) {
            // row.like = !row.like;
            if (row.like) {
                //添加到我喜欢
                axios({
                    url: "/collect/addMyLoveSong?id=" + row.id,
                    method: "post",
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
                    url: "/collect/deleteMyLoveSong?id=" + row.id,
                    method: "post",
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


    },


})

