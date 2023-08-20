new Vue({
    el: "#app",
    data() {
        return {
            items: [
                {pic: "../../image/tx.jpg", name: "陈奕迅"},
            ],
        };
    },
    mounted() {
        this.getSinger();
    },
    methods: {
        getSinger() {
            axios({
                url: "/collect/getCollectSinger",
                method: "get",
            }).then(resp => {
                if (resp.data.code == 1) {
                    this.items = resp.data.data;
                }
            })
        }
    }
})