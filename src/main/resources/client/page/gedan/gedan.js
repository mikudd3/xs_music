new Vue({
    el: "#app",
    data() {
        return {
            title: "希望十八岁你爱的人是八十岁在你身边的人",   //歌单专题
            introduction: "让你怦然心动",                 //歌单介绍
            pic: "",                                     //歌单图片
            name: "",
            imageurl: "5.png",
            text: "",
            songs: [],
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
                url: "/songlist/one?id=" + id,
            }).then(res => {
                let songlist = res.data.data;
                console.log(songlist)
                this.title = songlist.title;
                this.introduction = songlist.introduction;
                //歌单图片
                this.imageurl = songlist.pic;
            });
            //获取歌单的歌曲请求
            axios({
                method: "post",
                url: "/song/list2?id=" + id,
            }).then(rest => {
                this.songs = rest.data.data;
            })
        },


    }

})