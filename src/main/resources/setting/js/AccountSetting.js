new Vue({
    el: "#div",
    data: {
        dialogFormVisible4Phone: false,//绑定手机号窗口
        active: 0,//手机号绑定步骤
        dialogFormVisible4Pwd: false,//修改密码窗口
        userdata:{},//用户信息
        formdata:[], //页面信息

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
                    this.getuserinfo();
                } else {
                    this.$message.error(resp.data.msg);
                }
            })
        },
        //动态获取页面信息
        getuserinfo(){
            axios({
                url: "/user/getuserinfo",
                method: "get",
            }).then(resp => {
                if (resp.data.code == 1) {
                    this.formdata = resp.data.data;
                } else {
                    this.$message.error(resp.data.msg);
                }
            })
        },
        //弹出重新绑定手机的窗口
        "PhonedialogForm"(){
            this.dialogFormVisible4Phone = true;
            this.active = 0;

        },
        //下一步
        next(){
            if (this.active++ > 2) this.active = 0;
        },
        //
        PhoneEdit(){

        },
        //弹出修改密码的窗口
        PwddialogForm(){
            this.dialogFormVisible4Pwd = true;
            this.active = 0;
        }
    },
    mounted() {
        this.getUser();
    }
});