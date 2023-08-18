new Vue({
    el: "#app",
    data() {
        return {
            items: [
                {id: 1, pic: "../../image/tx.jpg", title: "陈奕迅"},
            ]
        };
    },
    mounted() {
        this.getSongList();
    },
    methods: {
        //获取用户信息
        getSongList() {
            axios({
                url: "/collect/getCollectSongList",
                method: "get",
            }).then(resp => {
                if (resp.data.code == 1) {
                    this.items = resp.data.data;
                    for (let i = 0; i < this.items.length; i++) {
                        this.items[i].pic = `/common/download?name=` + this.items[i].pic;
                    }
                }
            })
        },
    }
})