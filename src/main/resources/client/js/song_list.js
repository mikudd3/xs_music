new Vue({
    el: '#m3',
    data(){
        return {
            categories: [],
            song_list: [],
            title:"",
            pic:"",
            introduction:"",
            type:"",
            style_ids:"",
            create_time:""
        }
    },
    created() {
        // 调用API或其他方式获取categories数据
        // 假设获取到的数据是一个数组
        this.categories=[
            { name: '全部歌单', url: '#' },
            { name: '华语', url: '#' },
            { name: '粤语', url: '#' },
            { name: '欧美', url: '#' },
            { name: '日韩', url: '#' },
            { name: '轻音乐', url: '#' },
            { name: 'BGM', url: '#' },
            { name: '乐器', url: '#' }];
        this.pic="../../image/a(1).png";
        this.introduction="11111";
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
                this.song_list = res.data;
            })
        },

    },
    mounted(){
        this.listUsers();
    }
})