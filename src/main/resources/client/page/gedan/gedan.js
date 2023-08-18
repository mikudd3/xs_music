new Vue({
    el: "#app",
    data() {
        return {
            title: "希望十八岁你爱的人是八十岁在你身边的人",   //歌单专题
            introduction: "让你怦然心动",                 //歌单介绍
            pic: "",
            songs: [
                {
                    name:"111",
                    singerName:"222",
                    introduction:"333",
                }
            ],
        }
    },
    mounted() {
        this.getAll();
    },
    methods: {
        getAll() {
            const searchParams = new URLSearchParams(window.location.search);
            const id = searchParams.get('id');
            //获取歌单的信息请求
            axios({
                method: "post",
                url: "/songlist/one?id=" + id,
            }).then(res => {
                let songlist = res.data.data;
                console.log(songlist)
                this.title = songlist.title;
                this.introduction = songlist.introduction;
                //歌单图片
                this.pic = `/common/download?name=` + songlist.pic;
            });
            //获取歌单的歌曲请求
            axios({
                method: "post",
                url: "/song/list2?id=" + id,
            }).then(rest => {
                this.songs = rest.data.data;
            })
        },
        //点击歌曲输出歌曲id
        handleClick(id){
            console.log(id)
        },

    }

})

// 收藏
const collectButton = document.getElementById("collectButton");

collectButton.addEventListener("click", function() {
    collectButton.querySelector(".heart-icon").classList.toggle("active");
  
  // 根据点赞状态执行相应的操作
  if (collectButton.querySelector(".heart-icon").classList.contains("active")) {
  // 执行点赞操作
  } else {
  // 执行取消点赞操作
  }
});