new Vue({
    el: "#list",
    data: {
        listResout:[
            {
                //必要信息
                ImgUrl: 'http://www.170hi.com/wp-content/themes/beetube/images/nopic.png',
                song: 'https://www.ihaoge.net/server/1/287280938.mp3',
                name: '叮叮当',
                singer: '宝宝巴士',
                like:true
            },
            {
                ImgUrl: 'https://star.kuwo.cn/star/starheads/180/21/12/1142472669.jpg',
                song: 'https://www.ihaoge.net/server/1/283424829.mp3',
                name: '水中花(Live)',
                singer: '郁可唯',
                like:false
            },
            {
                ImgUrl: 'http://static.170hi.com/wp-content/themes/beetube/images/nopic.png',
                song: 'https://www.ihaoge.net/server/1/289548192.mp3',
                name: '僕らのスペクトラ',
                singer: '北谷洋',
                like:false
            }
        ],

    },
    methods: {
        //喜欢
        toggleFavorite(row) {
            row.like = !row.like;
            //添加进我喜欢的请求
        },
        //播放
        addtoplay(row){
            //信息塞进session域中
        }

    }
});