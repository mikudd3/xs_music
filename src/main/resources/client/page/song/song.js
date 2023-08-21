new Vue({
    el: "#app",
    data() {
        return {
            song: {
                name: "仰望星空",   //歌单专题
                singerName: "张杰",                 //歌单介绍
                pic: "../../image/a%20(1).png",
                lyric: "aaaaaaaaaaaaaaaaaaaaa",
            },
            content: '',
            userData:[{}],
            comments: [{
                name: '妖怪',
                create_time: "2010-10-1",
                content: "show time!",
            }]
        }
    },
    mounted() {
        this.getAll();
        this.getUser();
        this.getAllComments();
    },
    methods: {
        getUser() {
            axios({
                url: "/user/getUser",
                method: "get",
            }).then(resp => {
                if (resp.data.code == 1) {
                    this.userData = resp.data.data;
                    this.$message.error("获取用户");

                } else {
                    this.$message.error("resp.data.msg");
                }
            })
        },
        getAllComments() {
            this.searchParams = new URLSearchParams(window.location.search);
            const id = this.searchParams.get('id');
            console.log("user"+id)
            axios({
                method: "get",
                url: "/comment/gets",
                params: {
                    type: 0,
                    songId:id,
                }
            }).then(res => {
                if(res.data.code==1){
                    this.comments = res.data.data;
                    console.log(this.comments);
                }else{
                    this.$message.error("res.data.msg");
                }
            })
        },
        setComment() {
            this.searchParams = new URLSearchParams(window.location.search);
            const id = this.searchParams.get('id');
            {
                axios({
                    method: "post",
                    url: "/comment/add",
                    params: {
                        userId: this.userData.id,
                        type: 0,
                        content: this.content,
                        songId:id,
                    }
                }).then(ress => {
                    if(ress.data.code==1){
                        this.comments = ress.data.data;
                        this.content='';
                        this.getAllComments();
                        this.$message.error("评论成功");
                    }else{
                        this.$message.error(ress.data.msg);
                    }
                })
            }
        },

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
                }
            });
        },
    }
})