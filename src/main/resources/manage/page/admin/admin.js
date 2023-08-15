new Vue({
    el:"#app",
    data(){
    return{
        currentPage: 1,
        pageSize: 5,
        totalCount: 0,
        pagination: {},
        dataList:[
            {
                name:"zhangsan",
                create_time:"2020-1-1",
                zt:1
            }
        ],
        formData: {},//表单数据
        dialogFormVisible: false,//控制表单是否可见
        dialogFormVisible4Edit: false,//编辑表单是否可见
        admin: {
            name:"",
            zt:"",
        }
    }
    },
    methods:{
        //获取所有管理员信息
        getAll(){
            axios({
                method: "post",
                //查询所有管理员请求路径
                url: "#",
                data: {
                    currentPage: this.currentPage,
                    pageSize: this.pageSize,
                    admin: this.admin,
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
        //点击了修改
        //弹出编辑窗口
        handleUpdate(row) {
            //从当前行拿数据
            this.formData = row;
            this.dialogFormVisible4Edit = true;
        },
        //重置
        cz() {
            this.admin = {}
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
                    //删除管理员请求路径
                    url: "#" + row.id,
                }).then((res) => {
                    if (res.data.code == 1) {
                        this.$message.success(res.data.msg);
                    } else {
                        this.$message.error(res.data.msg);
                    }
                }).finally(() => {
                    //删除
                    this.getAll();
                });

            }).catch(() => {
                this.$message.info("取消删除")
            })
        },

        //修改点击了取消
        resetForm(){
            //重置表单
            this.formData = {};
            //收起表单
            this.dialogFormVisible4Edit = false;
        },


        //编辑
        //确认修改
        handleEdit() {
            //发送请求
            axios({
                method: "post",
                //修改管理员信息请求路径
                url: "#",
                data: this.formData
            }).then((res) => {
                this.dialogFormVisible4Edit = false;
                if (res.data.code == 1) {
                    //弹窗
                    this.$message.success("修改成功")
                    //操作成功，关闭窗口
                } else {
                    this.$message.error(res.data.msg)
                }
            }).finally(() => {
                //重新获取所有管理员信息
                this.getAll();
            })
        },
    }
})