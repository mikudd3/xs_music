new Vue({
    el: "#app",
    data() {
        return {
            imageUrl: '',
            userdata: {},

        };
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
                    this.imageUrl = this.userdata.tx;
                } else {
                    this.$message.error(resp.data.msg);
                }
            })
        },
        //回调函数上传成功的处理
        handleAvatarSuccess(res, file) {
            this.imageUrl = res.data;
        },
        //上传前的检验
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
                this.$message.error('上传头像图片只能是 JPG 格式!');
            }
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
        },
        //重新上传头像
        reupload() {
            this.imageUrl = '';
            this.$message.success("已重置")
        },
        //修改头像,上面仅仅是上传预览，没写入数据库
        alterAvator() {
            axios({
                url: "/user/update",
                method: "post",
                data: {
                    id: this.userdata.id,
                    tx: this.imageUrl,
                }
            }).then(resp => {
                if (resp.data.code == 1) {
                    this.$message.success("修改成功！")
                    this.getUser();
                } else {
                    this.$message.error(resp.data.msg);
                }
            })
        }

    }
})