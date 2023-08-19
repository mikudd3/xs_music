new Vue({
    el: "#app",
    data() {
        return {
            items: [
                {id: 1, pic: "../../image/tx.jpg", title: "史诗 • 神级BGM丨灵魂的震颤&心灵的洗涤史诗"},

            ],
            dialogFormVisible: false,
            formData: {},
            options:[],
        };
    },
    mounted() {
        this.getMyCreateSongList();
        this.getStyleName();
    },
    methods: {

        getStyleName() {
            axios({
                method: "post",
                url: "/style/getStyleName",
            }).then((res) => {
                let r = res.data;
                console.log(r)
                if (r.code == 1) {
                    this.options = r.data;
                } else {
                    this.$message.error(r.msg)
                }
            })
        },



        handleAvatarSuccess(resp) {
            this.imageUrl = resp.data;
        },
        addSongList() {
            this.dialogFormVisible = true;
        },
        handleAdd() {
            this.formData.pic = this.imageUrl;
            console.log(this.formData)
            axios({
                url: "/songlist/addSongList",
                method: "post",
                data:{
                    //songlist:this.formData,
                    user_id:"",
                    title:this.formData.title,
                    pic:this.formData.pic,
                    introduction:this.formData.introduction,
                    styleIds:this.formData.id,
                }
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