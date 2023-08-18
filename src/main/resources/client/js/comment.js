new Vue({
    el: '#main2',
    data() {
        return {
            all_comment: 89898,

            comments: [{
                id: 1,
                name: "妖怪",
                create_time:"2010-10-1",
                context: "show time!",
            }
            ]
        }
    },methods:{
        getComment(){

        },setComment(){

        },
        getAllComments(){
            axios({
                method: "get",
                url: "/comment/gets",
                params: {

                }
            }).then(res => {
                this.singers = res.data.data;
                console.log(this.singers);
            })
        }
    },mounted(){
        this.getAllComments();
    }
})