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

//获取轮播图
new Vue({
    el: '#slider',
    data() {
        return {
            slides: [
                {pic: '../../image/a (1).png'},
                {pic: '../../image/a (2).png'},
            ],
            currentIndex: 0,
            timerId: null
        };
    },
    computed: {
        currentSlide() {
            return this.slides[this.currentIndex];
        }
    },
    mounted() {
        // this.startAutoPlay();
        this.fetchSlides();
    },
    methods: {
        fetchSlides() {
            axios({
                url: "/banner/getlbt",
                method: "get"
            }).then(response => {
                if (response.data.code == 1) {
                    let r = response.data;
                    this.slides = r.data;
                    //进行图片处理
                    for (let i = 0; i < this.slides.length; i++) {
                        this.slides[i].pic = `/common/download?name=${this.slides[i].pic}`
                    }
                    console.log(r.data)
                    this.startAutoPlay();
                } else {
                    console.log(response)
                    this.$message.error(response.data.msg)
                }
            }).catch(error => {
                console.error('Error fetching slides:', error);
            });
        },
        nextSlide() {
            this.currentIndex = (this.currentIndex + 1) % this.slides.length;
        },
        prevSlide() {
            this.currentIndex = (this.currentIndex - 1 + this.slides.length) % this.slides.length;
        },
        startAutoPlay() {
            this.timerId = setInterval(() => {
                this.nextSlide();
            }, 1500);
        },
        stopAutoPlay() {
            clearInterval(this.timerId);
        }
    },
    beforeDestroy() {
        this.stopAutoPlay();
    }
})


new Vue({
    el: "#gedan",
    data() {
        return {
            songs: [
                {
                    id: 1,
                    pic: 'https://liquan-springboot-music.oss-cn-shanghai.aliyuncs.com/images/introduce_img/f1.jpg',
                    title: '每日最新单曲',
                    love: 4846948449,
                },
            ]
        };
    },
    mounted() {
        this.getSongList();
    },
    methods: {
        getSongList() {
            axios({
                method: "get",
                url: "/songlist/getSongList",
            }).then(resp => {
                let r = resp.data;
                if (r.code == 1) {
                    this.songs = r.data;
                } else {
                    this.$message.error(resp.data.msg);
                }
            })
        }
    }
})

new Vue({
    el: "#music",
    data: {
        // 正在播放的歌
        state:true,
        playsong:{
            ImgUrl:'',
            song:'',
            name:'暂无歌曲',
            singer:'无',
        },
        //播放列表抽屉
        drawer: false,
        direction:'rtl',
        //播放列表
        songlist:[
            {
                //必要信息
                ImgUrl:'http://www.170hi.com/wp-content/themes/beetube/images/nopic.png',
                song:'https://www.ihaoge.net/server/1/287280938.mp3',
                name:'叮叮当',
                singer:'宝宝巴士',
            },
            {
                ImgUrl:'https://star.kuwo.cn/star/starheads/180/21/12/1142472669.jpg',
                song:'https://www.ihaoge.net/server/1/283424829.mp3',
                name:'水中花(Live)',
                singer:'郁可唯',
            },
            {
                ImgUrl:'http://static.170hi.com/wp-content/themes/beetube/images/nopic.png',
                song:'https://www.ihaoge.net/server/1/289548192.mp3',
                name:'僕らのスペクトラ',
                singer:'北谷洋',
            }
        ],
        //当前序号
        songindex:0,
    },
    methods: {
        //获取初始歌曲,挂载触发
        fristsong(){
            this.playsong=this.songlist[this.songindex];
        },
        //播放
        play(){
            var mymusic=document.getElementById("musicer");
            mymusic.play();
            this.state=false;
        },
        //从歌单选择播放
        selectplay(row){
            this.songindex=row;
            this.playsong=this.songlist[this.songindex];
            var mymusic=document.getElementById("musicer");
            mymusic.load();
            this.play();
        },
        //从歌单中删除
        deletesong(row){
            if(this.songindex == row){
                this.songlist.splice(row, 1);
                if( this.songlist.length > 0){
                    if(row  == this.songlist.length){
                        this.playsong=this.songlist[row-1];
                    }else {
                        this.playsong=this.songlist[row];
                    }
                }else{
                    this.playsong={
                        ImgUrl:'',
                        song:'',
                        name:'暂无歌曲',
                        singer:'无',
                    };
                }
                var mymusic=document.getElementById("musicer");
                mymusic.load();
                this.play();
            }else {
                this.songlist.splice(row, 1);
            }
        },
        //暂停
        stop(){
            var mymusic=document.getElementById("musicer");
            mymusic.pause();
            this.state=true;
        },
        //上一首
        backup(){
            if((this.songindex-1) >= 0){
                this.songindex=this.songindex-1;
            }else{
                this.songindex = this.songlist.length-1;
            }

            this.playsong=this.songlist[this.songindex];
            var mymusic=document.getElementById("musicer");
            mymusic.load();
            this.play();
        },
        //下一首
        nextto(){
            this.songindex=((this.songindex+1)%this.songlist.length);
            this.playsong=this.songlist[this.songindex];
            var mymusic=document.getElementById("musicer");
            mymusic.load();
            this.play();
        },
        //开关抽屉
        changelist(){
            this.drawer = !this.drawer;
        }
    },
    mounted() {
        this.fristsong();
    }
});