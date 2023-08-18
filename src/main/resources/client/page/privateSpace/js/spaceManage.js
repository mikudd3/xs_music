new Vue({
    el: "#div",
    data: {
        avatarUrl: 'https://tupian.qqw21.com/article/UploadPic/2020-5/20205622141367241.jpg',
        iframeSrc: "like.html",
        userdata: {
            username: '翁修成',
            sex: 0,
            location: '广西',
            introduction: '广西老表'
        },
    },
    mounted() {
        this.getUser();
    },
    methods: {
        //获取用户信息
        getUser() {
            axios({
                url: "/user/getUser",
                method: "get",
            }).then(resp => {
                if (resp.data.code == 1) {
                    this.userdata = resp.data.data;
                    this.avatarUrl = `/common/download?name=` + this.userdata.tx;
                } else {
                    this.$message.error(resp.data.msg);
                }
            })
        },
        // 收藏
        findInfo: function () {
            this.iframeSrc = "songlist_collection.html"
        },
        //关注
        findAvator: function () {
            this.iframeSrc = "focus.html"
        },
        // 我创建的歌单
        findAccount: function () {
            this.iframeSrc = "like.html"
        }

    }
});