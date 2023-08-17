new Vue({
    el: "#container",
    data() {
        return {
            isPasswordLogin: false,
            phone: "",
            password: "",
            username: "",
        }
    },
    methods: {

        //点击发送验证码六十秒倒计时
        send() {
            console.log(typeof this.phone)
            // 3. 点击之后，禁用按钮，同时开启倒计时
            this.disabled = true
            // 控制显示数字的
            let i = 60
            btn.innerHTML = `${i}秒之后重新获取`
            let timer = setInterval(function () {
                i--
                // 在定时器里面不能用this，this执行的window
                btn.innerHTML = `${i}秒之后重新获取`

                // 4. 如果时间为0，则清除定时器，并且更改文字
                if (i < 0) {
                    clearInterval(timer)
                    btn.innerHTML = '获取验证码'
                    btn.disabled = false
                }
            }, 1000)
            axios({
                method: "post",
                url: "/user/send",
                data:{
                    phone:this.phone,
                }
            })
        },

        //切换密码或者验证码登录
        switchLoginMode() {
            var phone = document.querySelector('#phone');
            var password = document.querySelector('#password');
            var btn = document.querySelector('#btn');
            var switchBtn = document.querySelector('#switchBtn');

            if (this.isPasswordLogin) {
                // 切换到验证码登录
                switchBtn.innerHTML = '密码登录';
                document.querySelector('.header').innerHTML = '验证码登录';
                password.type = 'text';
                password.placeholder = '验证码';
                password.classList.remove('hidden');
                btn.classList.remove('hidden');
            } else {
                // 切换回密码登录
                switchBtn.innerHTML = '验证码登录';
                document.querySelector('.header').innerHTML = '手机号登录';
                password.type = 'password';
                password.placeholder = '密码';
                password.classList.add('hidden');
                btn.classList.add('hidden');
            }
            this.isPasswordLogin = !this.isPasswordLogin;
            //isPasswordLogin = !isPasswordLogin; // 切换登录模式
        },

        //点击登录
        login() {
            //发送请求
            console.log(this.isPasswordLogin)
            console.log(this.phone)
            console.log(this.password)
            //当this.isPasswordLogin为true的时候使用的是密码登录
            if (this.isPasswordLogin) {
                axios({
                    method: "post",
                    url: "/user/login",
                    data: {
                        phone: this.phone,
                        password: this.password,
                    }
                }).then(res => {
                    if (res.data.code == 1) {
                        //登录成功
                        alert("登录成功")
                    } else {
                        alert("登录失败")
                    }
                })
            } else {
                //使用验证码登录


            }

        },
    }

})