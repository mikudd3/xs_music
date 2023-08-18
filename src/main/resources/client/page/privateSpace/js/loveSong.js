new Vue({
    el: "#app",
    data() {
        return {
            song:"",
            songs: [

            ],
        }
    },
    mounted() {
        this.getAll();
    },
    methods: {
        //点击歌曲输出歌曲id
        handleClick(song) {
            console.log(song)
        },

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