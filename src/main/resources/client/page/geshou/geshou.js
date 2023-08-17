new Vue({
    el: '#main',
    data() {
        return {
            currentActivity: 'active',
            name: "杰伦",
            location: "中国",
            sex: "男",
            singers: [{
                name: "杰哥",
                pic: 'https://liquan-springboot-music.oss-cn-shanghai.aliyuncs.com/images/introduce_img/f1.jpg',
                url:'http://localhost/client/page/main/index.html',
            }

            ]
        }
    },
    methods: {
        getsex(sex) {
            this.sex = sex;
            console.log(sex);
            this.getallSingers();
        },

        getlocation(location) {
            this.location = location;
            console.log(location);
            this.getallSingers();
        }, getallSingers() {
            //方法
            axios({
                method: "get",
                url: "/singer/getSingers",
                params: {
                    sex: this.sex,
                    location: this.location,
                }
            }).then(res => {
                this.singers = res.data;
                console.log(this.singers);
            })
        },mouted(){
                // this.startAutoPlay();
                this.getallSingers();
}

    }
})