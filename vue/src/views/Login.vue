<template xmlns:font-size="http://www.w3.org/1999/xhtml" xmlns:text-align="http://www.w3.org/1999/xhtml">

  <div style="width: 100%;height: 100vh; background-color: darkblue;overflow: hidden" :style="bg">
    <div style=" width: 400px; margin: 150px auto">
      <div style="text-align: center ;color:#cccccc;font-size: 30px;padding: 30px 0">火车售票系统</div>
      <el-form ref="form" :model="form" size="normal" :rules="rules">
        <el-form-item prop="phone">
          <el-input prefix-icon="el-icon-user-solid" v-model="form.phone" prop="phone"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" v-model="form.password" show-password prop="password"></el-input>
        </el-form-item>
        <!--        <el-form-item>-->
        <!--          <el-radio v-model="form.role" :label="1">管理员</el-radio>-->
        <!--          <el-radio v-model="form.role" :label="2">普通用户</el-radio>-->
        <!--        </el-form-item>-->
        <el-form-item>
          <div>
            <el-button style="width: 100%" type="primary" @click="login">登 录</el-button>
          </div>
          <div style="margin: 30px 0">
            <el-button type="text" @click="register">注册</el-button>
<!--            <el-button type="text" disabled>文字按钮</el-button>-->
<!--            <el-button style="width: 100%" type="primary" @click="register" >注 册</el-button>-->

          </div>

        </el-form-item>
      </el-form>
    </div>
  </div>
</template>


<style scoped>

</style>

<script>
import request from "../utils/request";

export default {
  name: "Login",
  components: {},
  data() {
    return {
      form: {},
      rules: {
        phone: [
          {required: true, message: '请输入您的手机号', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
      },
      // 加背景图片
      bg: {
        backgroundImage: "url(" + require("@/assets/Login.jpg") + ")",
        backgroundRepeat: "no-repeat",
        backgroundSize: "100% 100%"
      }
    }
  },
  methods: {
    login() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          request.post("/user/login", this.form).then(res => {
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "登录成功"
              })
              sessionStorage.setItem("user", JSON.stringify(res.data))  //获取用户信息
              this.$router.push("/")//登录成功之后进行页面的跳转，跳转到主页
            } else {
              this.$message({
                type: "error",
                message: "登录失败"
              })
            }
          })
        }
      })

    },
    register() {
      this.$router.push("/register")
    }
  }
}


</script>