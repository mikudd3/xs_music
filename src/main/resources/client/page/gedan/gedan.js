new Vue({
    el: "#app",
    data() {
        return {
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
    },
    methods: {
        playAll() {
            console.log(this.songs)
            sessionStorage.setItem("songs", this.songs);
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


// // 收藏
// const collectButton = document.getElementById("collectButton");
//
// collectButton.addEventListener("click", function () {
//     collectButton.querySelector(".heart-icon").classList.toggle("active");
//
//     // 根据点赞状态执行相应的操作
//     if (collectButton.querySelector(".heart-icon").classList.contains("active")) {
//         // 执行点赞操作
//     } else {
//         // 执行取消点赞操作
//     }
// });