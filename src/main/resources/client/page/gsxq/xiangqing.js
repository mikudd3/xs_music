new Vue({
    el: "#app",
    data() {
        return {
            name: "许嵩",              //歌手名字
            introduction: "著名作词人、作曲人、唱片制作人、歌手。内地独立音乐之标杆人物，有音乐鬼才之称。2009年独立出版首张词曲全创作专辑《自定义》，2010年独立出版第二张词曲全创作专辑《寻雾启示》，两度掀起讨论热潮，一跃成为内地互联网讨论度最高的独立音乐人。2011年加盟海蝶音乐，推出第三张词曲全创作专辑《苏格拉没有底》，发行首月即在大陆地区摘下唱片销量榜冠军，轰动全国媒体，并拉开全国巡回签售及歌迷见面会。",
            sex: "男",                 //歌手性别
            birth: "2020-01-01",       //歌手生日
            location: "中国",          //歌手国籍
            pic: "",                   //歌手图片
            songs: [],                 //歌曲列表
            imageUrl: "https://bkimg.cdn.bcebos.com/pic/d1a20cf431adcbef76091cafaff839dda3cc7cd93159?x-bce-process=image/resize,m_lfit,w_536,limit_1/format,f_auto",
            text: "",
        }
    },
    mounted() {
        this.getAll();
    },
    methods: {
        //进入界面获取信息
        getAll() {
            const searchParams = new URLSearchParams(window.location.search);
            const id = searchParams.get('id');
            console.log(id)
            axios({
                method: "post",
                url: "/singer/one?id=" + id,
            }).then(res => {
                let singer = res.data.data;
                console.log(singer)
                this.name = singer.name;
                this.introduction = singer.introduction;
                this.birth = singer.birth;
                this.location = singer.location;
                this.imageUrl = singer.pic;
                console.log(this.imageurl)
            });
            axios({
                method: "post",
                url: "/song/list?id=" + id,
            }).then(rest => {
                this.songs = rest.data.data;
            })
        },
        //点击歌曲输出歌曲id
        handleClick(id){
            console.log(id)
        },

    }

})