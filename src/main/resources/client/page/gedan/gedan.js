new Vue({
    el:"#app",
    data(){
        return{
            gedan:[{
                title: "希望十八岁你爱的人是八十岁在你身边的人",   //歌单专题
                introduction: "让你怦然心动",                 //歌单介绍
                pic:"5.png",                                //歌单图片
            }],

            songs:[],

        }
    },
    // mounted() {
    //     this.getAll();
    // },
    methods: {
        //进入界面获取信息
        //获取歌单的信息
        getAll(){
            const searchParams = new URLSearchParams(window.location.search);
            const id = searchParams.get('id');
            // alert(),
            axios({
                method:"get",
                url:"/songlist/gedan?id="+id,
            }).then(res=>{
                if (res.data.code == 1) {
                    this.gedan = res.data.data;
                    for (let index = 0; index < this.items.length; index++) {
                        this.gedan[index].pic ="../image/a (1).png";
                    }
                }

            })
        },

        //获取歌单的歌曲
        // getSongs(){
        //     const searchParams = new URLSearchParams(window.location.search);
        //     const id = searchParams.get('id');
        //     axios({
        //         method:"post",
        //         url:"/songlist/one?id="+id,
        //     }).then(res=>{
        //         let songlist = res.data.data;
        //     })
        // }
    }

})