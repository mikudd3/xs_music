new Vue({
    el: "#app",
    data() {
        return {
            name: "",
            password: "",
        }

    },
    methods: {
        dosubmit() {
            axios({
                url: "/admin/login",
                method: "post",
                data: {
                    name: this.name,
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