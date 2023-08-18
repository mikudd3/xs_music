//获取登录用户的头像
new Vue({
    el: "#col10",
    data() {
        return {
            imageUrl: "../../image/tx.jpg",
        }
    },
    mounted() {
        this.getUser();
    },
    methods: {
        getUser() {
            axios({
                url: "/user/getUser",
                method: "get",
            }).then(resp => {
                if (resp.data.code == 1) {
                    this.imageUrl = `/common/download?name=` + resp.data.data.tx;
                } else {
                    this.$message.error(resp.data.msg);
                }
            })
        },
    }
})





new Vue({
    el: "#music",
    data: {
        // 正在播放的歌
        state: true,
        playsong: {
            ImgUrl: '',
            song: '',
            name: '暂无歌曲',
            singer: '无',
        },
        //播放列表抽屉
        drawer: false,
        direction: 'rtl',
        //播放列表
        songlist: [
            {
                //必要信息
                ImgUrl: 'http://www.170hi.com/wp-content/themes/beetube/images/nopic.png',
                song: 'https://www.ihaoge.net/server/1/287280938.mp3',
                name: '叮叮当',
                singer: '宝宝巴士',
            },
            {
                ImgUrl: 'https://star.kuwo.cn/star/starheads/180/21/12/1142472669.jpg',
                song: 'https://www.ihaoge.net/server/1/283424829.mp3',
                name: '水中花(Live)',
                singer: '郁可唯',
            },
            {
                ImgUrl: 'http://static.170hi.com/wp-content/themes/beetube/images/nopic.png',
                song: 'https://www.ihaoge.net/server/1/289548192.mp3',
                name: '僕らのスペクトラ',
                singer: '北谷洋',
            }
        ],
        //当前序号
        songindex: 0,
    },
    methods: {
        //获取初始歌曲,挂载触发
        fristsong() {
            this.playsong = this.songlist[this.songindex];
        },
        //播放
        play() {
            var mymusic = document.getElementById("musicer");
            mymusic.play();
            this.state = false;
        },
        //从歌单选择播放
        selectplay(row) {
            this.songindex = row;
            this.playsong = this.songlist[this.songindex];
            var mymusic = document.getElementById("musicer");
            mymusic.load();
            this.play();
        },
        //从歌单中删除
        deletesong(row) {
            if (this.songindex == row) {
                this.songlist.splice(row, 1);
                if (this.songlist.length > 0) {
                    if (row == this.songlist.length) {
                        this.playsong = this.songlist[row - 1];
                    } else {
                        this.playsong = this.songlist[row];
                    }
                } else {
                    this.playsong = {
                        ImgUrl: '',
                        song: '',
                        name: '暂无歌曲',
                        singer: '无',
                    };
                }
                var mymusic = document.getElementById("musicer");
                mymusic.load();
                this.play();
            } else {
                this.songlist.splice(row, 1);
            }
        },
        //暂停
        stop() {
            var mymusic = document.getElementById("musicer");
            mymusic.pause();
            this.state = true;
        },
        //上一首
        backup() {
            if ((this.songindex - 1) >= 0) {
                this.songindex = this.songindex - 1;
            } else {
                this.songindex = this.songlist.length - 1;
            }

            this.playsong = this.songlist[this.songindex];
            var mymusic = document.getElementById("musicer");
            mymusic.load();
            this.play();
        },
        //下一首
        nextto() {
            this.songindex = ((this.songindex + 1) % this.songlist.length);
            this.playsong = this.songlist[this.songindex];
            var mymusic = document.getElementById("musicer");
            mymusic.load();
            this.play();
        },
        //开关抽屉
        changelist() {
            this.drawer = !this.drawer;
        }
    },
    mounted() {
        this.fristsong();
    }
});

new Vue({
    el: "#main",
    data: {
        iframeSrc: "main.html",
    },
    methods: {
        // 首页
        findmain: function () {
            this.iframeSrc = "main.html"
        },
        //歌单
        findsonglis: function () {
            this.iframeSrc = "../gedan/song_list.html"
        },
        //歌手
        findSinger: function () {
            this.iframeSrc = "../geshou/geshou.html"
        },
        // 个人主页
        findmysong: function () {
            this.iframeSrc = "../privateSpace/spaceManage.html"
        }

    }
});