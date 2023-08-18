new Vue({
    el:"#app",
    data(){
        return{
            title: "希望十八岁你爱的人是八十岁在你身边的人",   //歌单专题
            introduction: "让你怦然心动",                 //歌单介绍
            pic:"",                                     //歌单图片
            name: "",
            imageurl: "5.png",
            songs:[],

        }
    },
    methods: {



        //进入界面获取信息
        //获取歌单的信息
        getAll(){
            alert(),
            axios({
                method:"post",
                url:"/songlist/one?id="+2,
            }).then(res=>{
                let songlist = res.data.data;
                console.log(songlist)
                this.title = songlist.title;
                this.introduction = songlist.introduction;
            })
        },

        //获取歌单的歌曲
        getSongs(){
            axios({
                method:"post",
                url:"/songlist/one?id="+2,
            }).then(res=>{
                let songlist = res.data.data;
                console.log(songlist)
            })
        }


    }

})