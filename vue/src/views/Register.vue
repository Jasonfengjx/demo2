<template xmlns:font-size="http://www.w3.org/1999/xhtml" xmlns:text-align="http://www.w3.org/1999/xhtml">

  <div style="width: 100%;height: 100vh; background-color: darkblue;overflow: hidden" :style="bg">
    <div style=" width: 400px; margin: 150px auto">
      <div style="text-align: center ;color:#cccccc;font-size: 30px;padding: 30px 0">欢迎注册</div>
      <el-form ref="form" :model="form" size="normal" :rules="rules">
        <el-form-item prop="phone">
          <el-input prefix-icon="el-icon-user-solid" v-model="form.phone" prop="phone"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" v-model="form.password" show-password prop="password"></el-input>

          <el-input prefix-icon="el-icon-lock" v-model="form.confirm" show-password prop="confirm" style="margin: 30px 0"></el-input>

        </el-form-item>
        <!--        <el-form-item>-->
        <!--          <el-radio v-model="form.role" :label="1">管理员</el-radio>-->
        <!--          <el-radio v-model="form.role" :label="2">普通用户</el-radio>-->
        <!--        </el-form-item>-->
        <el-form-item>
          <el-button style="width: 100%" type="primary" @click="register">注 册</el-button>
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

  name: "Register",
  components:{},
  data() {
    return {
      form: {},
      rules: {
        username: [
          {required: true, message: '请输入电话', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
        confirm: [
          {required: true, message: '请确认密码', trigger: 'blur'},
        ],
      },

      // 加背景图片
      bg: {
        backgroundImage: "url(" + require("@/assets/Register.jpg") + ")",
        backgroundRepeat: "no-repeat",
        backgroundSize: "100% 100%"
      }
    }
  },
  methods: {
    register(){
      this.$refs['form'].validate((valid) => {
        if (valid) {
          request.post("/user/register",this.form).then(res=>
          {
            if(this.form.password != this.form.confirm){
              this.$message({
                type: "error",
                message: "注册失败"
              })
              return
            }
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "注册成功"
              })
              this.$router.push("/login")//登录成功之后进行页面的跳转，跳转到主页
            } else {
              this.$message({
                type: "error",
                message: "注册失败"
              })
            }
          })
        }
      })
    }
  }
}


</script>