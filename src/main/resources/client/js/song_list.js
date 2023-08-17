new Vue({
    el: '#m3',
    data(){
        return {
            items: [
                {
                    id: "",
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

    methods:{
        getPriceRange(range) {
            axios({
                url: "/songlist/songfl",
                method: "get",
                params: {style_name: range}
            }).then(resp => {

                    this.items = resp.data;
                    for (let index = 0; index < this.items.length; index++) {
                        this.items[index].pic = `../../image/a (7).png`
                    }
            })
        }
    },
})