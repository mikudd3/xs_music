new Vue({
    el: "#app",
    data() {
        return {
            username: "",
            password: "",
        }

    },
    methods: {
        dosubmit() {
            axios({
                url: "/admin/login",
                method: "post",
                data: {
                    username: this.username,
                    password: this.password
                }
            }).then(resp => {
                if(resp.data.code == 1){
                    //登录成功
                    location.href="page/main/main.html";
                }else {
                    this.$message.error(resp.data.msg)
                }
            })
        },

    },
})