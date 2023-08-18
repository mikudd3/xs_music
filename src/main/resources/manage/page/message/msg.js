new Vue({
    el: '#app',
    data() {
        return {
            //男用户数量
            menCount:1,
            //女用户数量
            womenCount:2,
            //用户总数量
            userCount: 100,

            //歌曲总数量
            songCount: 500,

            //歌手总数量
            singerCount: 100,
            //男歌手数量
            singerCountMan: 100,
            //女歌手数量
            singerCountWomen: 100,

            //歌单数量
            song_list_Count: 50,

            //存贮国籍的数组
            chartData:[],
            cityData:[],

            //存贮风格的数组
            styleName:[],
            styleCount:[],
        };


    },
    mounted() {
        this.getUserCount();
        this.getSongCount();
        this.getSingerCount();
        this.getSongListCount();
        this.fetchData();
        this.getSongListStyle();
    }
    ,
    methods: {
        getUserCount() {
            axios({
                method: "post",
                url: "/user/getUserCount",
            }).then((res) => {
                let r = res.data;
                console.log(r)
                if (r.code == 1) {
                    this.userCount = r.data.userCount;
                    this.menCount = r.data.menCount;
                    this.womenCount = r.data.womenCount;
                    this.bingtu1();
                } else {
                }
            })
        },


        //绘制用户饼图男女比例表
        bingtu1(){
            var option = {
                color: ['pink', 'blue' ],
                series: [
                    {
                        type: 'pie',
                        data: [
                            {
                                value: this.womenCount,
                                name: '女'
                            },
                            {
                                value: this.menCount,
                                name: '男'
                            },
                        ],
                        label: {
                            show: true,
                            formatter: '{b}: {c} ({d}%)'
                        }
                    }
                ]
            };
            var chart = echarts.init(document.getElementById('chart1'));
            chart.setOption(option);
        },
        getSongCount() {
            axios({
                method: "get",
                url: "/song/getSongCount",
            }).then((res) => {
                let r = res.data;
                if (r.code == 1) {
                    this.songCount = r.data;
                } else {
                }
            })
        },
        getSingerCount() {
            axios({
                method: "get",
                url: "/singer/getSingCount",
            }).then((res) => {
                let r = res.data;
                if (r.code == 1) {
                    this.singerCount = r.data.singerCount;
                    this.singerCountMan = r.data.singerCountMan;
                    this.singerCountWomen = r.data.singerCountWomen;
                    this.bingtu2();
                } else {
                }

            })
        },
        //绘制歌手饼图男女比例表
        bingtu2(){
            var option = {
                color: ['pink', 'blue' ],
                series: [
                    {
                        type: 'pie',
                        data: [
                            {
                                value: this.singerCountWomen,
                                name: '女'
                            },
                            {
                                value: this.singerCountMan,
                                name: '男'
                            },
                        ],
                        label: {
                            show: true,
                            formatter: '{b}: {c} ({d}%)'
                        }
                    }
                ]
            };
            var chart = echarts.init(document.getElementById('chart3'));
            chart.setOption(option);
        },

        getSongListCount() {
            axios({
                method: "get",
                url: "/songlist/getSongListCount",
            }).then((res) => {
                let r = res.data;
                if (r.code == 1) {
                    this.song_list_Count = r.data;
                } else {
                    this.$message.error(res.data.msg);
                }
            })
        },
        getSongListStyle(){
            axios({
                method: "get",
                url: "/songlist/getStyle",
            }).then((res) => {
                let mapData = res.data.data;
                console.log(mapData)
                for (const [key, value] of Object.entries(mapData)) {
                    this.styleCount.push(value);
                    /*this.chartData.put(0)
                    this.chartData.put(0)*/
                    this.styleName.push(key);
                }
                this.styleName.push(" ")
                this.styleName.push(" ")
                console.log(this.styleName)
                console.log(this.styleCount)
                this.huitu2()
            })
        },


        //获取歌手国籍信息
        fetchData() {
            // 使用axios、fetch或其他方式从后端获取数据
            // 假设获取到的数据为dataFromBackend
            <!--SELECT location ,count(location) from singer GROUP BY location ORDER BY COUNT(location) DESC LIMIT 8;-->
            axios({
                method: "get",
                url: "/singer/getSingerLocationCategory",
            }).then(resp => {
                let mapData = resp.data.data;
                console.log(mapData)
                for (const [key, value] of Object.entries(mapData)) {
                    this.chartData.push(value);
                    /*this.chartData.put(0)
                    this.chartData.put(0)*/
                    this.cityData.push(key);
                }
                this.cityData.push(" ")
                this.cityData.push(" ")
                console.log(this.chartData)
                console.log(this.cityData)
                this.huitu1()
            })
        },

        //柱状体 歌手国籍
        huitu1(){
            var option = {
                xAxis: {
                    type: 'category',
                    data: this.cityData
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    data: this.chartData,
                    type: 'bar',
                    barWidth: 30,
                    barCategoryGap: '90%',
                    barGap: '100%',
                    itemStyle: {
                        color: '#3366CC' // 设置圆柱体的颜色
                    }
                }]
            };
            var chart = echarts.init(document.getElementById('chart4'));
            chart.setOption(option);
        },

        //柱状体 风格
        huitu2(){
            var option = {
                xAxis: {
                    type: 'category',
                    data: this.styleName,
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    data: this.styleCount,
                    type: 'bar',
                    barWidth: 30,
                    barCategoryGap: '90%',
                    barGap: '100%',
                    itemStyle: {
                        color: '#3366CC' // 设置圆柱体的颜色
                    }
                }]
            };
            var chart = echarts.init(document.getElementById('chart2'));
            chart.setOption(option);
        }
    },
})
