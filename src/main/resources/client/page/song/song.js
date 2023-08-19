new Vue({
    el: "#app",
    data() {
        return {
            song: {
                name: "仰望星空",   //歌单专题
                singerName: "张杰",                 //歌单介绍
                pic: "../../image/a%20(1).png",
                lyric: "aaaaaaaaaaaaaaaaaaaaa",
            }
        }
    },
    mounted() {
        this.getAll();
    },
    methods: {
        getAll() {
            const searchParams = new URLSearchParams(window.location.search);
            const id = searchParams.get('id');
            // alert(id)
            //获取歌单的信息请求
            axios({
                method: "post",
                url: "/song/getSong?id=" + id,
            }).then(res => {
                if (res.data.code == 1) {
                    this.song = res.data.data
                    this.song.pic = `/common/download?name=` + this.song.pic
                }
            });
        },
    }
})