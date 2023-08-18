new Vue({
    el: "#app",
    data() {
        return {
            name: "仰望星空",   //歌单专题
            singer: "张杰",                 //歌单介绍
            pic:"../../image/a%20(1).png",
            lyric: "aaaaaaaaaaaaaaaaaaaaa",
        }
    },
    mounted() {
        this.getAll();
    },
    methods: {
        getAll() {
            const searchParams = new URLSearchParams(window.location.search);
            const id = searchParams.get('id');
            //获取歌单的信息请求
            axios({
                method: "post",
                url: "/songlist/song?id=" + id,
            }).then(res => {
                let song = res.data.data;
                console.log(song)
                this.name = song.name;
                this.singer = song.singer;
                this.lyric=song.lyric;
                //歌曲图片
                this.pic = `/common/download?name=` + song.pic;
            });
        },
    }
})