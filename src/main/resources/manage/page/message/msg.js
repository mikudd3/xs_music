new Vue({
    el: '#app',
    data() {
        return {
            userCount: 100,
            songCount:500,
            singerCount:100,
            song_list_Count:50
        }

    }
    ,
    methods:{
        huitu1(){
            var option = {
                series: [
                    {
                        type: 'pie',
                        data: [
                            {
                                value: 100,
                                name: '男'
                            },
                            {
                                value: 200,
                                name: '女'
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
        huitu2(){
            var option = {
                xAxis: {
                    type: 'category',
                    data: ['华语', '粤语', '欧美', '日韩', '轻音乐','乐器',''],
                    barWidth: 30
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    data: [150, 200, 300, 180, 250,230],
                    type: 'bar',
                    barWidth: 30,
                    barCategoryGap: '100%',
                    barGap: '100%',
                    itemStyle: {
                        color: '#3366CC' // 设置圆柱体的颜色
                    }
                }]
            };
            var chart = echarts.init(document.getElementById('chart2'));
            chart.setOption(option);
        },
        huitu3(){
            var option = {
                series: [
                    {
                        type: 'pie',
                        data: [
                            {
                                value: 100,
                                name: '男'
                            },
                            {
                                value: 150,
                                name: '女'
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
        huitu4(){
            var option = {
                xAxis: {
                    type: 'category',
                    data: ['中国', '韩国', '意大利', '新加坡', '美国','西班牙','',''],
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    data: [18, 12, 5, 6, 9,2,10],
                    type: 'bar',
                    barWidth: 30,
                    barCategoryGap: '100%',
                    barGap: '100%',
                    itemStyle: {
                        color: '#3366CC' // 设置圆柱体的颜色
                    }
                }]
            };
            var chart = echarts.init(document.getElementById('chart4'));
            chart.setOption(option);
        }
    }
})