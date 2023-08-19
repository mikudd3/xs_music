new Vue({
    el: "#app",
    data() {
        return {
            //tang
            content:"alkjdfkl",
            all_comment: 233,
            comments: [{
                name: '妖怪',
                create_time: "2010-10-1",
                content: "show time!",
            }
            ],
            items: {
                id: 1,
                title: "希望十八岁你爱的人是八十岁在你身边的人",   //歌单专题
                introduction: "",                 //歌单介绍
                pic: "",
                isCollected: false,
            },
            searchParams: "",
            songs: [
                {
                    id: 1,
                    name: "111",
                    singerName: "222",
                    introduction: "333",
                    isLiked: false,
                    url: "",
                },
                {
                    id: 2,
                    name: "111",
                    singerName: "222",
                    introduction: "333",
                    isLiked: false,
                    url: "",
                }
            ],
            songLists: [
                {
                    id: 1,
                    title: ""
                }
            ],
            //用于判断收藏是否成功
            sl: 0,
        }
    },
    mounted() {
        this.getAll();
        this.getAllComments();
    },
    methods: {
        getAllComments() {
            axios({
                method: "get",
                url: "/comment/gets",
                params: {
                    type: 1,
                }
            }).then(res => {
                this.comments = res.data.data;
                console.log(this.comments);
            })
        },
        setComment() {
            this.searchParams = new URLSearchParams(window.location.search);
            //const searchParams = new URLSearchParams(window.location.search);
            const id = this.searchParams.get('id');
            {
                axios({
                    method: "get",
                    url: "/comment/add",
                    params: {
                        user_id:id,
                        type: 1,
                        content:this.content,
                    }
                }).then(ress=>{
                    this.comments = ress.data.data;
                    this.getAllComments();
                })
            }
        },
        playAll() {
            console.log(this.songs)
            sessionStorage.setItem("songs", JSON.stringify(this.songs));
        },
        getAll() {
            this.searchParams = new URLSearchParams(window.location.search);
            //const searchParams = new URLSearchParams(window.location.search);
            const id = this.searchParams.get('id');
            //获取歌单的信息请求
            axios({
                method: "post",
                url: "/songlist/one?id=" + id,
            }).then(res => {
                let songlist = res.data.data;
                console.log(songlist)
                this.title = songlist.title;
                this.introduction = songlist.introduction;
                //歌单图片
                this.pic = `/common/download?name=` + songlist.pic;
            });
            //获取歌单的歌曲请求
            axios({
                method: "post",
                url: "/song/list2?id=" + id,
            }).then(rest => {
                this.songs = rest.data.data;
            })
        },
        //点击歌曲输出歌曲id
        handleClick(song) {
            sessionStorage.setItem("songs", song);
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
        }

    },


})

