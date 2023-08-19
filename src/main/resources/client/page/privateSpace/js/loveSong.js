new Vue({
    el: "#app",
    data() {
        return {
            song: "",
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
            ],
        }
    },
    mounted() {
        this.getAll();
    },
    methods: {
        //点击歌曲输出歌曲id
        handleClick(song) {
            sessionStorage.setItem("songs", JSON.stringify(song));
        },
        getAll() {
            axios({
                method: "get",
                url: "/collect/getMyLoveSong",
            }).then(res => {
                if (res.data.code == 1) {
                    this.songs = res.data.data;
                } else {
                    this.$message.error(res.data.msg);
                }
            })
        },

        //点击播放全部
        playAll() {
            console.log(this.songs)
            sessionStorage.setItem("songs", JSON.stringify(this.songs));
        },

        //喜欢
        toggleFavorite(row) {
            // row.like = !row.like;
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
    }

})