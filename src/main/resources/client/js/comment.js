new Vue({
    el: '#main2',
    data() {
        return {
            all_comment: 89898,
            comments: [{
                id: 1,
                name: '妖怪',
                create_time: "2010-10-1",
                content: "show time!",
            }
            ]
        }
    }, methods: {
        getUp() {

        }, setComment() {
            axios({
                method: "get",
                url: "/comment/add",
                params: {
                    type: 1,
                }
            })
            this.getAllComments();
        },
        getAllComments() {
            axios({
                method: "get",
                url: "/comment/gets",
                params: {
                    type: 1,
                }
            }).then(res => {
                this.comments = res.data.data;
                console.log(this.comments);
                this.all_comment=this.comments.size;
                console.log(this.all_comment);
            })
        }
    }, mounted() {
        this.getAllComments();
    }
})