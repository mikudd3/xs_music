new Vue({
    el: "#div",
    data: {
        userdata:{},
        formdata:[], //表单信息
        rules: {
            username: [
                {required: true, message: '请输入用户名', trigger: 'blur'},
                {min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur'}
            ],
            name: [
                {required: true, message: '请输入昵称', trigger: 'blur'},
                {min: 1, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur'}
            ],
            yhsf: [
                {required: true, message: '请选择用户身份', trigger: 'blur'}
            ],
            sex: [
                {required: true, message: '请选择性别', trigger: 'blur'},
            ],
            sjh: [
                {required: true, message: '请输入手机号', trigger: 'blur'},
                {min: 11, max: 11, message: '长度为11的号码', trigger: 'blur'}
            ],
        }
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
                } else {
                    this.$message.error(resp.data.msg);
                }
            })
        },
        //动态获取信息
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
        //修改信息
        "updata"() {
            axios({
                url: "/user/updata",
                method: "post",
                data: this.formdata,
            }).then(resp => {
                if (resp.data.code == 1) {
                    this.$message.success("修改成功！")
                    this.getuserinfo();
                } else {
                    this.$message.error(resp.data.msg);
                }
            })
        }
    },
    mounted() {
        this.getUser();
    }
});