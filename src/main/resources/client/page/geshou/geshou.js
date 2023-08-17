new Vue({
    el: '#main',
    data() {
        return {
            name:"杰伦",
            singers:[{
                name:"杰哥",
                pic: 'https://liquan-springboot-music.oss-cn-shanghai.aliyuncs.com/images/introduce_img/f1.jpg',
            }
            ,{
                    name:"阿伟",
                    pic: 'https://y.qq.com/n/ryqq/playlist/7060349831',
                }
            ,{
                    name:"阿伟",
                    pic: 'https://y.qq.com/n/ryqq/playlist/7060349831',
                }
            ,{
                    name:"阿伟",
                    pic: 'https://y.qq.com/n/ryqq/playlist/7060349831',
                }
            ,{
                    name:"阿伟",
                    pic: 'https://y.qq.com/n/ryqq/playlist/7060349831',
                }
                ]
        }
    },
    methods: {

        getlocation(location){
            console.log(location);
        }

    }
})