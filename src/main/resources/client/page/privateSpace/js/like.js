new Vue({
    el: "#app",
    data() {
        return {
            items: [
                {id: 1, pic: "../../image/tx.jpg", title: "史诗 • 神级BGM丨灵魂的震颤&心灵的洗涤史诗"},

            ],
            dialogFormVisible: false,
            formData: {},
        };
    },
    mounted() {
        this.getMyCreateSongList();
    },
    methods: {
        handleAvatarSuccess(resp) {
            this.imageUrl = resp.data;
        },
        addSongList() {
            this.dialogFormVisible = true;
        },
        handleAdd() {
            this.formData.pic = this.imageUrl;
            axios({
                url: "/songlist/addSongList",
                method: "post",
            }).then(resp => {
                if (resp.data.code == 1) {
                    this.$message.success("添加成功");
                    this.getMyCreateSongList();
                } else {
                    this.$message.error(resp.data.msg);
                }
            })
        },
        getMyCreateSongList() {
            axios({
                url: "/songlist/getMyCreateSongList",
                method: "get",
            }).then(resp => {
                if (resp.data.code == 1) {
                    this.items = resp.data.data;
                    //对图片进行处理
                    for (let i = 0; i < this.items.length; i++) {
                        this.items[i].pic = `/common/download?name=` + this.items[i].pic;
                    }
                } 
            })
        }
    }
})