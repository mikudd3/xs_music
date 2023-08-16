new Vue({
    el: "#div",
    data: {
        dialogFormVisible4Phone: false,//绑定手机号窗口
        trueyzm: '',//真的的验证码
        active: 0,//手机号绑定步骤,修改密码步骤
        phoneSet: {
            oldphone: '',//旧号码
            newphone: '',//新号码
            yzm: '',//输入的验证码

        },
        //设置密码表单
        pwdSet: {
            phone: '',//号码
            yzm: '',//验证码
            pwd1: '',//密码1
            pwd2: '',//密码2
        },
        dialogFormVisible4Pwd: false,//修改密码窗口
        userdata: {
            phone: '18154643660',
        },//用户信息
        formdata: [], //页面信息
    },
    methods: {
        //获取用户信息
        getUser() {
            axios({
                url: "/user/getUser",
                method: "get",
            }).then(resp => {
                if (resp.data.code == 1) {
                    this.userdata = resp.data.data;
                } else {
                    this.$message.error(resp.data.msg);
                }
            })
        },
        //动态获取页面信息
        getuserinfo() {
            axios({
                url: "/user/getuserinfo",
                method: "get",
            }).then(resp => {
                if (resp.data.code == 1) {
                    this.formdata = resp.data.data;
                    this.formdata.phone = this.hidePhoneNumber(resp.data.data.phone);
                } else {
                    this.$message.error(resp.data.msg);
                }
            })
        },
        //隐藏手机号
        hidePhoneNumber(phoneNumber) {
            if (phoneNumber.length >= 7) {
                return phoneNumber.slice(0, 3) + '****' + phoneNumber.slice(-4);
            } else {
                return phoneNumber;
            }
        },
        //请求发送验证码，并获取验证码用于验证
        "sentMasage"(phone) {
            axios({
                url: "/user/getuserinfo",
                method: "post",
                data: phone,
            }).then(resp => {
                if (resp.data.code == 1) {
                    this.trueyzm =
                        this.$message.success("发送验证码成功！");
                } else {
                    this.$message.error(resp.data.msg);
                }
            })
        },
        //弹出重新绑定手机的窗口
        "PhonedialogForm"() {
            this.dialogFormVisible4Phone = true;
            //重置表单数据
            this.active = 0;
            this.phoneSet.oldphone = '';
            this.phoneSet.newphone = '';
            this.phoneSet.yzm = '';
            this.phoneSet.trueyzm = '';//真正的验证码
        },
        //验证手机号是否规范
        validatePhoneNumber(phoneNumber) {
            const regExp = /^1[3456789]\d{9}$/;
            return regExp.test(phoneNumber);
        },
        //下一步
        nexta() {
            switch (this.active) {
                //验证输入的旧号码是否正确
                case 0 : {
                    if (this.phoneSet.oldphone != this.userdata.phone) {
                        this.$message.error("输入号码不正确!");
                        return;
                    } else {
                        this.$message.success("成功");
                    }
                }
                    ;
                    break;
                //向新号码发验证码
                case 1 : {
                    if (!this.validatePhoneNumber(this.phoneSet.newphone)) {
                        this.$message.error("手机号不合法!");
                        return;
                    } else {
                        this.sentMasage();
                    }
                }
                    ;
                    break;
                //确认验证码
                case 2 : {
                    if (this.phoneSet.yzm != this.trueyzm) {
                        this.$message.error("输入验证码不正确!");
                        return;
                    } else {
                        this.PhoneEdit();
                    }
                }
                    ;
                    break;
            }
            if (this.active++ > 2) this.active = 0;
        },
        //发送修改手机号请求
        PhoneEdit() {
            axios({
                url: "/user/update",
                method: "post",
                data: {
                    id: this.userdata.id,
                    phone: this.phoneSet.newphone,
                }
            }).then(resp => {
                if (resp.data.code == 1) {
                    this.$message.success("修改成功！");
                } else {
                    this.$message.error(resp.data.msg);
                }
            })
        },
        //弹出修改密码的窗口
        PwddialogForm() {
            this.dialogFormVisible4Pwd = true;
            //重置表单数据
            this.active = 0;
            this.pwdSet.phone = '';
            this.pwdSet.yzm = '';
            this.pwdSet.pwd1 = '';
            this.pwdSet.pwd2 = '';
        },
        //下一步
        nextb() {
            switch (this.active) {
                //验证输入的号码是否正确,并发验证码
                case 0 : {
                    if (!this.validatePhoneNumber(this.pwdSet.phone)) {
                        this.$message.error("手机号不合法!");
                        return;
                    } else {
                        if (this.pwdSet.phone != this.userdata.phone) {
                            this.$message.error("输入号码不正确!");
                            return;
                        } else {
                            this.$message.success("成功");
                            this.sentMasage();
                        }
                    }
                }
                    ;
                    break;
                //核验验证码
                case 1 : {
                    if (this.pwdSet.yzm != this.trueyzm) {
                        this.$message.error("输入验证码不正确!");
                        return;
                    } else {
                        this.$message.success("验证成功！");
                    }
                }
                    ;
                    break;
                //验证两次密码是否一致
                case 2 : {
                    if (this.pwdSet.pwd1 != this.pwdSet.pwd2) {
                        this.$message.error("两次密码不正确!");
                        return;
                    } else {
                        this.sentPwd();
                    }
                }
                    ;
                    break;
            }
            if (this.active++ > 2) this.active = 0;
        },
        //提交新密码
        sentPwd() {
            axios({
                url: "/user/update",
                method: "post",
                data: {
                    id: this.userdata.id,
                    password: this.pwdSet.pwd1,
                }
            }).then(resp => {
                if (resp.data.code == 1) {
                    this.$message.success("修改成功！");
                } else {
                    this.$message.error(resp.data.msg);
                }
            })
        }
    },
    mounted() {
        this.getUser();
    }
});