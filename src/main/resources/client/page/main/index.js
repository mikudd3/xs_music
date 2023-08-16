new Vue({
    el: '#slider',
    data() {
        return {
            slides: [
                {url: '../../image/a (1).png'},
                {url: '../../image/a (2).png'},
            ],
            currentIndex: 0,
            timerId: null
        };
    },
    computed: {
        currentSlide() {
            return this.slides[this.currentIndex];
        }
    },
    mounted() {
        // this.startAutoPlay();
        this.fetchSlides();
    },
    methods: {
        fetchSlides() {
            axios({
                url: "/banner/getlbt",
                method: "get"
            }).then(response => {
                if (response.data.code == 1) {
                    let r = response.data;
                    this.slides = r.data;
                    //进行图片处理
                    for (let i = 0; i < this.slides.length; i++) {
                        this.slides[i] = `/common/download?name=${this.slides[i]}`
                    }
                    console.log(r.data)
                    this.startAutoPlay();
                } else {
                    console.log(response)
                    this.$message.error(response.data.msg)
                }
            }).catch(error => {
                console.error('Error fetching slides:', error);
            });
        },
        nextSlide() {
            this.currentIndex = (this.currentIndex + 1) % this.slides.length;
        },
        prevSlide() {
            this.currentIndex = (this.currentIndex - 1 + this.slides.length) % this.slides.length;
        },
        startAutoPlay() {
            this.timerId = setInterval(() => {
                this.nextSlide();
            }, 1500);
        },
        stopAutoPlay() {
            clearInterval(this.timerId);
        }
    },
    beforeDestroy() {
        this.stopAutoPlay();
    }
})


new Vue({
    el: "#gedan",
    data() {
        return {
            songs: [
                {
                    id: 1,
                    pic: 'https://liquan-springboot-music.oss-cn-shanghai.aliyuncs.com/images/introduce_img/f1.jpg',
                    title: '每日最新单曲',
                },
                {
                    id: 2,
                    pic: 'https://liquan-springboot-music.oss-cn-shanghai.aliyuncs.com/images/introduce_img/f13.jpg',
                    title: '每日最新网络单曲',
                },
            ]
        };
    },
    methods() {

    }
})