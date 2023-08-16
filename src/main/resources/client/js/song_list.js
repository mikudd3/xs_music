Vue.createApp({
    data(){
        return {
            song_list: [],
            title:"",
            pic:"",
            introduction:"",
            type:"",
            style_ids:"",
            create_time:""
        }
    },

    methods:{
        listUsers(){
            axios({
                method:"get",
                url:"#",
                params:{
                    title:this.title,
                    pic:this.pic,
                    introduction:this.introduction,
                    type:this.type,
                    style_ids:this.style_ids,
                    create_time: this.create_time
                }
            }).then(res => {
                this.song_list = res.song_list;
            })
        },

    },
    mounted(){
        this.listUsers();
    }
}).mount("#song");