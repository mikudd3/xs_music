new Vue({
    el: '#main',
    data() {
        return {
            currentActivity: 'active',
            sex: '',
            location: '',
            singers: [{
                name: "杰哥",
                pic: 'https://liquan-springboot-music.oss-cn-shanghai.aliyuncs.com/images/introduce_img/f1.jpg',
                url: 'http://localhost/client/page/main/index.html',
            }

            ]
        }
    },
    methods: {
        getsex(sex) {
            this.sex = sex;
            console.log(this.sex);
            console.log(this.location)
            this.getallSingers();
        },

        getlocation(location) {
            this.location = location;
            console.log(this.sex);
            console.log(this.location);
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
                this.singers = res.data.data;
                console.log(this.singers);
            })
        },

    },
    mounted() {
        console.log(this.sex);
        console.log(this.location)
        this.getallSingers();
    }
})