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
            }
                , {
                    name: "阿伟",
                    pic: 'https://y.qq.com/n/ryqq/playlist/7060349831',
                }
                , {
                    name: "阿伟",
                    pic: 'https://y.qq.com/n/ryqq/playlist/7060349831',
                }
                , {
                    name: "阿伟",
                    pic: 'https://y.qq.com/n/ryqq/playlist/7060349831',
                }
                , {
                    name: "阿伟",
                    pic: 'https://y.qq.com/n/ryqq/playlist/7060349831',
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
        }, getallSingers(sex) {
            //方法
            axios({
                method: "get",
                url: "#",
                params: {
                    sex: this.sex,
                    location: this.location,
                }
            }).then(res => {
                this.singers = res.data;
                console.log(this.singers);
            })
        }

    }
})