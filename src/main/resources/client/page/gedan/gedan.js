new Vue({
    el:"#app",
    data(){
        return{
            title: "希望十八岁你爱的人是八十岁在你身边的人",   //歌单专题
            introduction: "让你怦然心动",                 //歌单介绍
            pic:"",                                     //歌单图片
            name: "",
            imageUrl: "",
        }
    },
    methods: {
        // //钩子函数，VUE对象初始化完成后自动执行
        // created() {
        //     this.getAll();
        // },
        // handleAvatarSuccess(response, file, fileList) {
        //     this.imageUrl = response.data
        // },
        // getAll(){
        //     console.log(this.text)
        //     this.getAll();
        //
        //     axios({
        //         method: "post",
        //         url: "/song/page",
        //         data: {
        //             currentPage: this.currentPage,
        //             pageSize: this.pageSize,
        //             singerName: this.singerName,
        //         }
        //     }).then((res) => {
        //         let r = res.data;
        //         if (r.code == 1) {
        //             this.dataList = r.data.records;
        //             this.totalCount = r.data.total;
        //         } else {
        //             this.$message.error(r.msg)
        //         }
        //     })
        // },

        //进入界面获取信息
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
    }

})