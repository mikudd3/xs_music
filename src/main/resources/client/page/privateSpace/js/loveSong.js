new Vue({
    el: "#app",
    data() {
        return {
            songs: [],
        }
    },
    mounted() {
        this.getAll();
    },
    methods: {
        getAll() {
            axios({
                method: "get",
                url: "/collect/getMyLoveSong",
            }).then(res => {
                if (res.data.code == 1) {
                    this.songs = res.data.data;
                } else {
                    this.$message.error(res.data.msg);
                }

            })
        },
    }

})