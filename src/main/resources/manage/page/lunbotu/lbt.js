new Vue({
    el: '#app',
    data() {
        return {
            currentPage: 1,
            pageSize: 5,
            totalCount: 0,
            pagination: {},
            dataList: [
                {
                    pic: "../../image/tx.jpg",
                }
            ],//当前页要展示的列表数据
            formData: {},//表单数据
            dialogFormVisible4Edit: false,//编辑表单是否可见
            imageUrl: '../../image/tx.jpg',
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
                url: "/banner/page",
                data: {
                    currentPage: this.currentPage,
                    pageSize: this.pageSize,
                }
            }).then((res) => {
                let r = res.data;
                this.dataList = r.data.records;
                this.totalCount = r.data.total;
                console.log(this.totalCount)
            })

        },
        //弹出编辑窗口
        handleUpdate(row) {
            //根据id查询数据
            axios({
                method: "get",
                url: "/banner/selectById?id=" + row.id,
            }).then((res) => {
                this.formData = res.data.data;
                this.dialogFormVisible4Edit = true;
            })
        },
        //编辑
        handleEdit() {
            this.formData.url = this.imageUrl;
            //发送请求
            axios({
                method: "Put",
                url: "/banner/update",
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