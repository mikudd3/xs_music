new Vue({
    el: "#list",
    data: {
        listResout: [
            {
                //必要信息
                id:30,
                pic: 'http://www.170hi.com/wp-content/themes/beetube/images/nopic.png',
                url: 'https://www.ihaoge.net/server/1/287280938.mp3',
                name: '叮叮当',
                singerName: '宝宝巴士',
            },
            {
                pic: 'https://star.kuwo.cn/star/starheads/180/21/12/1142472669.jpg',
                url: 'https://www.ihaoge.net/server/1/283424829.mp3',
                name: '水中花(Live)',
                singerName: '郁可唯',
            },
            {
                pic: 'http://static.170hi.com/wp-content/themes/beetube/images/nopic.png',
                url: 'https://www.ihaoge.net/server/1/289548192.mp3',
                name: '僕らのスペクトラ',
                singerName: '北谷洋',
            }
        ],

    },
    methods: {
        //喜欢
        toggleFavorite(row) {
            if (row.like) {
                //添加到我喜欢
                axios({
                    url: "collect/addMyLoveSong",
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
                    url: "collect/deleteMyLoveSong",
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
        }

    }
});