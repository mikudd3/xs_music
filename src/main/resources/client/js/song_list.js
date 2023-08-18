new Vue({
    el: '#m3',
    data() {
        return {
            items: [
                {
                    id: 1,
                    title: "飞得更高！！！",
                    pic: "../../image/a (7).png",
                    love: "",
                }

            ],
        }
    },
    mounted() {
        this.getPriceRange();
    },
    methods: {
        getPriceRange(range) {
            axios({
                url: "/songlist/songfl",
                method: "get",
                params: {style_name: range}
            }).then(resp => {
                if (resp.data.code == 1) {
                    this.items = resp.data.data;
                    for (let index = 0; index < this.items.length; index++) {
                        this.items[index].pic = `/common/download?name-` + this.items[index].pic;
                    }
                }
            })
        }
    },
})