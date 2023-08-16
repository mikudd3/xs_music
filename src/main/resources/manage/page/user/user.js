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
                    username: "zhangsan",
                    sfz: "4521333151051",
                    sex: "男",
                    phone: 1234567,
                    location:"广西",
                    email: "1234@qq.com",
                    create_time: "2023-7-7",
                    zt: 1
                }
            ],//当前页要展示的列表数据
            formData: {},//表单数据
            dialogFormVisible: false,//控制表单是否可见
            dialogFormVisible4Edit: false,//编辑表单是否可见
            rules: {//校验规则
                username: [{required: true, message: '用户名为必填项', trigger: 'blur'}],
                sex: [{required: true, message: '请选择性别', trigger: 'change'}],
                yhsf: [{required: true, message: '请选择身份', trigger: 'change'}]
            },
            imageUrl: '../../image/tx.jpg',
            user: {
                username: "",
                zt:"",
            },
        }
    },
    //钩子函数，VUE对象初始化完成后自动执行
    created() {
        this.getAll();
    },
    methods: {
        //列表
        //获取所有用户
        getAll() {
            axios({
                method: "post",
                url: "/user/page",
                data: {
                    currentPage: this.currentPage,
                    pageSize: this.pageSize,
                    user: this.user,
                }
            }).then((res) => {
                if (res.data.code == 1){
                    let r = res.data;
                    this.dataList = r.data.records;
                    this.totalCount = r.data.total;
                    console.log(this.totalCount)
                }else{
                    this.$message.error(res.data.msg)
                }

            })
        },
        //重置表单
        resetForm() {
            this.formData = {};
            this.dialogFormVisible4Edit = false;
        },
        //弹出编辑窗口
        handleUpdate(row) {
            //从当前行拿数据
            this.formData = row;
            this.dialogFormVisible4Edit = true;
        },
        cz() {
            this.user = {}
        },
        //编辑
        handleEdit() {
            //发送请求
            console.log(this.formData)
            axios({
                method: "post",
                url: "/user/update",
                data: this.formData
            }).then((res) => {
                console.log(res)
                this.dialogFormVisible4Edit = false;
                if (res.data.code == 1) {
                    //弹窗
                    this.$message.success("修改成功")
                    //操作成功，关闭窗口
                } else {
                    this.$message.error(res.data.msg)
                }
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
                    url: "/user/deleteById?id=" + row.id,
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