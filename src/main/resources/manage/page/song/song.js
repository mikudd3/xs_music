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
                    singerName: "陈奕迅",
                    name: "富士山下",
                    sex: "男",
                    birth: "中国香港",
                    location: "中国香港",
                    introduction: "What's Going On…?",
                    lyric: "作词 : 林夕 作曲 : Christopher Chak 编曲 : 陈珀/C. Y. Kong 制作人 : 梁荣骏 " +
                        "拦路雨偏似雪花 饮泣的你冻吗" + "这风褛我给你磨到有襟花" + "连调了职也不怕 怎么始终牵挂" + "苦心选中今天想车你回家" +
                        "原谅我不再送花 伤口应要结疤" + "花瓣铺满心里坟场才害怕" + "如若你非我不嫁 彼此终必火化" + "一生一世等一天需要代价" +
                        "谁都只得那双手靠拥抱亦难任你拥有" + "要拥有必先懂失去怎接受" + "曾沿着雪路浪游 为何为好事泪流" + "谁能凭爱意要富士山私有" +
                        "何不把悲哀感觉假设是来自你虚构" + "试管里找不到它染污眼眸" + "前尘硬化像石头 随缘地抛下便逃走" + "我绝不罕有 往街里绕过一周" +
                        "我便化乌有" + "情人节不要说穿 只敢抚你发端" + "这种姿态可会令你更心酸" + "留在汽车里取暖 应该怎么规劝" + "怎么可以将手腕忍痛划损" +
                        "人活到几岁算短 失恋只有更短" + "归家需要几里路谁能预算" + "忘掉我跟你恩怨 樱花开了几转" + "东京之旅一早比一世遥远" +
                        "谁都只得那双手靠拥抱亦难任你拥有" + "要拥有必先懂失去怎接受" + "曾沿着雪路浪游 为何为好事泪流" + "谁能凭爱意要富士山私有" +
                        "何不把悲哀感觉假设是来自你虚构" + "试管里找不到它染污眼眸" + "前尘硬化像石头 随缘地抛下便逃走" + "我绝不罕有 往街里绕过一周" +
                        "我便化乌有" + "谁都只得那双手靠拥抱亦难任你拥有" + "要拥有必先懂失去怎接受" + "曾沿着雪路浪游 为何为好事泪流" + "谁能凭爱意要富士山私有" +
                        "何不把悲哀感觉假设是来自你虚构" + "试管里找不到它染污眼眸" + "前尘硬化像石头 随缘地抛下便逃走" + "我绝不罕有 往街里绕过一周" +
                        "我便化乌有" + "你还嫌不够 我把这陈年风褛 送赠你解咒",
                    url: "",
                }
            ],//当前页要展示的列表数据
            formData: {},//表单数据
            fileList: [],
            uploadDialogVisible: false,
            dialogFormVisible: false,//控制表单是否可见
            dialogFormVisible4Edit: false,
            singerName: "",
            options: ["陈奕迅", "孙燕姿", "周杰伦"], // 存放从后端获取的选项数据
            imageUrl: "",
            video: "",
        }
    },
    //钩子函数，VUE对象初始化完成后自动执行
    created() {
        this.getAll();
        this.getSingerName();
    },
    methods: {
        remove() {
            this.singerName = "";
        },
        handleAvatarSuccess(response, file, fileList) {
            this.imageUrl = response.data
        },
        openUploadDialog() {
            this.uploadDialogVisible = true;
        },
        handleUploadSuccess(response, file, fileList) {
            // 处理文件上传成功后的逻辑
            this.video = response.data;
        },
        getSingerName() {
            axios({
                method: "get",
                url: "/song/getSingerName",
            }).then((res) => {
                let r = res.data;
                if (r.code == 1) {
                    this.options = r.data;
                } else {
                    this.$message.error(r.msg)
                }
            })
        },
        //列表
        getAll() {
            axios({
                method: "post",
                url: "/song/page",
                data: {
                    currentPage: this.currentPage,
                    pageSize: this.pageSize,
                    singerName: this.singerName,
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
            // this.formData.pic = this.imageUrl;
            // this.formData.url = this.video;
            //发送请求
            axios({
                method: "POST",
                url: "/song/add",
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
                    this.$message.error(res.data.msg);
                }

            }).finally(() => {
                this.getAll();
            })
        },
        //弹出编辑窗口
        handleUpdate(row) {
            this.formData = row;
            // this.imageUrl = row.pic;
            // this.video = row.url;
            this.dialogFormVisible4Edit = true;
        },
        //编辑
        handleEdit() {
            // this.formData.pic = this.imageUrl;
            // this.formData.url = this.video;
            //发送请求
            axios({
                method: "post",
                url: "/song/update",
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
            this.$confirm("此操作将永久删除该数据，是否继续？", "提示", {
                type: "info"
            }).then(() => {
                //做删除业务
                //根据id查询数据
                axios({
                    method: "post",
                    url: "/song/delete?id=" + row.id,
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