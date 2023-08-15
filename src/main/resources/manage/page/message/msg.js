new Vue({
    el: '#app',
    data() {
        return {
            userCount: 100,
            songCount: 500,
            singerCount: 100,
            song_list_Count: 50,
        };


    },
    mounted() {
        this.getUserCount();
        this.getSongCount();
        this.getSingerCount();
        this.getSongListCount();
    }
    ,
    methods: {
        getUserCount() {
            axios({
                method: "get",
                url: "/user/getUserCount",
            }).then((res) => {
                let r = res.data;
                if (r.code == 1) {
                    this.userCount = r.data;
                } else {
                    this.$message.success(res.data.msg);
                }

            })
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
                    this.$message.success(res.data.msg);
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
                    this.singerCount = r.data;
                } else {
                    this.$message.success(res.data.msg);
                }

            })
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
                    this.$message.success(res.data.msg);
                }

            })
        },
    },
})


//歌手国籍图表
new Vue({
    el: "#chart4",
    data() {
        return {
            chartData: [], // 存储从后端获取的数据
            cityData: [],
        }
    },
    mounted() {
        this.fetchData(); // 从后端获取数据
    },
    methods: {
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
                    this.cityData.push(key);
                }
                console.log(this.chartData)
                console.log(this.cityData)
            })
            this.drawChart(); // 数据获取后绘制图表
        },
        drawChart() {
            const chartInstance = echarts.init(this.$refs.chart4); // 通过ref获取HTML元素
            const option = {
                xAxis: {
                    type: 'category',
                    data: this.cityData,
                },
                yAxis: {
                    type: 'value',
                },
                series: [
                    {
                        data: this.chartData,
                        type: 'bar',
                        barWidth: 30,
                        barCategoryGap: '100%',
                        barGap: '100%',
                        itemStyle: {
                            color: '#3366CC', // 设置圆柱体的颜色
                        },
                    },
                ],
            };
            chartInstance.setOption(option); // 绘制图表
        },
    }
})
