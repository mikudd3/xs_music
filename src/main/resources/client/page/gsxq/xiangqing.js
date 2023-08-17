new Vue({
    el:"#app",
    data(){
        return{
            name:"许嵩",              //歌手名字
            introduction:"著名作词人、作曲人、唱片制作人、歌手。内地独立音乐之标杆人物，有音乐鬼才之称。2009年独立出版首张词曲全创作专辑《自定义》，2010年独立出版第二张词曲全创作专辑《寻雾启示》，两度掀起讨论热潮，一跃成为内地互联网讨论度最高的独立音乐人。2011年加盟海蝶音乐，推出第三张词曲全创作专辑《苏格拉没有底》，发行首月即在大陆地区摘下唱片销量榜冠军，轰动全国媒体，并拉开全国巡回签售及歌迷见面会。",
            sex: "男",                 //歌手性别
            birth:"2020-01-01",       //歌手生日
            location:"中国",          //歌手国籍
            pic:"",                   //歌手图片
            songs:[
                {
                    name:"素颜",
                    introduction:"素颜",
                },
                {
                    name:"有何不可",
                    introduction:"自定义",
                },
            ],                 //歌曲列表
            imageurl:"all.jpg",
        }
    },
    methods: {
        handleClick(id) {
            // 在这里处理获取歌曲ID的操作
            console.log("点击了歌曲ID：" + id);
        },
        get(){
            console.log(this.text)
            this.getAll();
        },

        //进入界面获取信息
        getAll(){
            axios({
                method:"post",
                url:"/singer/one?id="+4,
            }).then(res=>{
                let singer = res.data.data;
                console.log(singer)
                this.name = singer.name;
                this.introduction = singer.introduction;
                this.birth = singer.birth;
                this.location = singer.location;
                this.imageurl = singer.pic;
                console.log(this.imageUrl)
            });
            axios({
                method:"post",
                url:"/song/list?id="+4,
            }).then(rest=>{
                this.songs = rest.data.data;
            })
        },
    }

})