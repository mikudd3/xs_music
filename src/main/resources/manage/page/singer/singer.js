new Vue({
    el: "#app",
    data() {
        return {
            currentPage: 1,
            pageSize: 5,
            totalCount: 0,
            pagination: {},
            dataList: [
                {
                    pic: "../../image/tx.jpg",
                    name: "陈奕迅",
                    sex: "男",
                    birth: "中国香港",
                    location: "中国香港",
                    introduction: "陈奕迅（Eason Chan），" +
                        "1974年7月27日出生于中国香港，祖籍广东省东莞市，中国香港流行乐男歌手、演员、作曲人，" +
                        "毕业于英国金斯顿大学。 1995年参加第14届新秀歌唱大赛并获得冠军，同年正式出道。1996年发行个人首张专辑《陈奕迅》。" +
                        "1997年主演个人首部电影《旺角大家姐》。1998年凭借歌曲《天下无双》在乐坛获得关注。2000年发行的歌曲《K歌之王》奠定其在歌坛的地位。" +
                        "2001年发行流行摇滚风格的专辑《反正是我》。2003年发行个人首张概念专辑《黑·白·灰》，专辑中的歌曲《十年》获得第4届音乐风云榜十大金曲奖；" +
                        "同年凭借专辑《Special Thanks to》获得第14届台湾金曲奖最佳国语男歌手以及最佳专辑奖。 2005年发行粤语专辑《U87》，" +
                        "专辑中的歌曲《浮夸》成为其歌唱生涯的代表作品之一。2006年起连续九年获得叱咤乐坛流行榜颁奖典礼我最喜爱的男歌手奖。" +
                        "2007年获得十大劲歌金曲最受欢迎男歌星奖。2010年凭借剧情片《金钱帝国》获得星光大典港台年度电影男演员奖。" +
                        "2012年发行舞曲风格的粤语专辑《…3mm》。2015年凭借专辑《米·闪》获得第26届台湾金曲奖最佳国语男歌手奖。" +
                        "2016年举行“Another Eason's LIFE世界巡回演唱会”。2018年凭借专辑《C'mon in~》获得第29届台湾金曲奖最佳国语男歌手奖 ；" +
                        "同年发行专辑《L.O.V.E.》；同年，其主演的电影《卧底巨星》上映。 2020年7月11日在香港举办《Live is so much better with Music Eason Chan Charity Concert》慈善音乐会。",
                    fans: 100000000000
                }
            ],//当前页要展示的列表数据
            formData: {},//表单数据
            dialogFormVisible: false,//控制表单是否可见
            dialogFormVisible4Edit: false,
            name: "",
            imageUrl: "",
        }
    },
    //钩子函数，VUE对象初始化完成后自动执行
    created() {
        this.getAll();
    },
    methods: {
        handleAvatarSuccess(response, file, fileList) {
            this.imageUrl = response.data
            console.log(this.imageUrl)
        },
        //列表
        getAll() {
            axios({
                method: "post",
                url: "/singer/page",
                data: {
                    currentPage: this.currentPage,
                    pageSize: this.pageSize,
                    name: this.name,
                }
            }).then((res) => {
                let r = res.data;
                if (r.code == 1) {
                    this.dataList = r.data.records;
                    this.totalCount = r.data.total;
                } else {
                    this.$message.error(r.msg)
                }
            })

        },
        //弹出添加窗口
        handleCreate() {
            this.dialogFormVisible = true;
            this.resetForm();
        },
        //重置表单
        resetForm() {
            this.formData = {};
        },
        //添加
        handleAdd() {
            this.formData.pic = this.imageUrl;
            //发送请求
            axios({
                method: "POST",
                url: "/singer/add",
                data: this.formData
            }).then((res) => {
                let r = res.data;
                if (r.code == 1) {
                    //添加成功
                    this.$message.success("添加成功");
                    //关闭窗口
                    this.dialogFormVisible = false;
                } else {
                    //添加失败
                    this.$message.success(res.data.msg);
                }

            }).finally(() => {
                this.getAll();
            })
        },
        //弹出编辑窗口
        handleUpdate(row) {
            this.formData = row;
            this.dialogFormVisible4Edit = true;
            this.imageUrl = "";
        },
        //编辑
        handleEdit() {
            this.formData.pic = this.imageUrl;
            //发送请求
            axios({
                method: "post",
                url: "/singer/update",
                data: this.formData
            }).then((res) => {
                //弹窗
                this.$message.success("修改成功")
                //操作成功，关闭窗口
                this.dialogFormVisible4Edit = false;
            }).finally(() => {
                this.getAll();
            })
        },

        // 删除
        handleDelete(row) {
            //弹出提示框
            this.$confirm("此操作将永久删除该歌手并将其关联的歌全部删除，是否继续？", "提示", {
                type: "info"
            }).then(() => {
                //做删除业务
                //根据id查询数据
                axios({
                    method: "post",
                    url: "/singer/delete?id=" + row.id,
                }).then((res) => {
                    if (res.data.code == 1) {
                        this.$message.success(res.data.msg);
                    } else {
                        this.$message.error(res.data.msg);
                    }
                }).finally(() => {
                    this.getAll();
                });

            }).catch(() => {
                this.$message.info("取消删除")
            })
        },
        handleCurrentChange(val) {
            this.currentPage = val;
            this.getAll();
        },
        handleSizeChange(val) {
            this.pageSize = val;
            this.getAll();
        }
    }
})