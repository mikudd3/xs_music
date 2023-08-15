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
                    title: "陈奕迅粤语经典",
                    introduction: "陈奕迅的经典粤语歌曲",
                    style: "粤语"
                }
            ],//当前页要展示的列表数据
            formData: {},//表单数据
            dialogFormVisible: false,//控制表单是否可见
            dialogFormVisible1: false,
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
        },
        //列表
        getAll() {
            axios({
                method: "post",
                url: "/songlist/page",
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
        //弹出编辑窗口
        handleUpdate(row) {
            //根据id查询数据
            axios({
                method: "get",
                url: "/songlist/selectById?id=" + row.id,
            }).then((res) => {
                this.formData = res.data.data;
                this.dialogFormVisible4Edit = true;
            })
        },
        //编辑
        handleEdit() {
            this.formData.image = this.imageUrl;
            //发送请求
            axios({
                method: "post",
                url: "/songlist/update",
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
                    url: "/songlist/delete?id=" + row.id,
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