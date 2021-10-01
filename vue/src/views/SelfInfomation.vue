<template xmlns:font-size="http://www.w3.org/1999/xhtml" xmlns:text-align="http://www.w3.org/1999/xhtml">
  <div style="width: 100vw">
    <div style="width:100%;margin: 10px 0">
      <el-button type="primary" @click="handleEdit" class="el-icon-circle-plus-outline"> 修改</el-button>
      <el-button type="primary" class="el-icon-bottom-right"> 导出</el-button>
      <el-button type="primary" class="el-icon-upload2"> 导入</el-button>
      <el-button type="primary" class="el-icon-download"> 下载</el-button>
    </div>
    <div style="width: 100%">
      <el-card class="box-card">
        <template #header>
          <div class="card-header">
            <i class="el-icon-user"></i>
            <span> 个人信息  </span>
          </div>
        </template>
        <div>
          <div style="margin: 30px">
            <i class="el-icon-suitcase"></i>
            <span> 个人姓名  </span>
            <el-tag>{{ user.name }}</el-tag>
          </div>
          <div style="margin: 30px">
            <i class="el-icon-notebook-1"></i>
            <span> 电话号码  </span>
            <el-tag>{{ user.phone }}</el-tag>
          </div>
          <div style="margin: 30px">
            <i class="el-icon-house"></i>
            <span> 住址  </span>
            <el-tag>{{ user.address }}</el-tag>
          </div>
          <div style="margin: 30px">
            <i class="el-icon-question"></i>
            <span> 性别  </span>
            <el-tag>{{ user.sex }}</el-tag>
          </div>
          <div style="margin: 30px">
            <i class="el-icon-chat-dot-square"></i>
            <span> 邮箱  </span>
            <el-tag>{{ user.email }}</el-tag>
          </div>
          <div style="margin: 30px">
            <i class="el-icon-place"></i>
            <span> 身份证号  </span>
            <el-tag>{{ user.identity }}</el-tag>
          </div>
          <div style="margin: 30px">
            <i class="el-icon-water-cup"></i>
            <span> 年龄  </span>
            <el-tag>{{ user.age }}</el-tag>
          </div>
        </div>
      </el-card>
    </div>
  </div>
  <el-dialog title="提示" v-model="dialogVisible" width="30%">
    <el-form :model="this.form" label-width="120px">
      <el-form-item label="电话号码">
        <el-input v-model="this.form.phone" style="width: 80%"></el-input>
      </el-form-item>
      <el-form-item label="用户名">
        <el-input v-model="this.form.name" style="width: 80%"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input prefix-icon="el-icon-lock" v-model="this.form.password" show-password prop="password"
                  style="width: 80%"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="this.form.address" style="width: 80%"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-radio v-model="this.form.sex" label="男">男</el-radio>
        <el-radio v-model="this.form.sex" label="女">女</el-radio>
        <el-radio v-model="this.form.sex" label="未知">未知</el-radio>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="this.form.email" style="width: 80%"></el-input>
      </el-form-item>
      <el-form-item label="身份证">
        <el-input v-model="this.form.identity" style="width: 80%"></el-input>
      </el-form-item>
      <el-form-item label="年龄">
        <el-input v-model="this.form.age" style="width: 80%"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
         <el-button @click="dialogVisible = false">取 消</el-button>
         <el-button type="primary" @click="save()">确 定</el-button>
       </span>
    </template>
  </el-dialog>

</template>
<style>

</style>

<script>
// @ is an alias to /src

import request from "../utils/request";

export default {
  name: 'SI',
  components: {},

  data() {
    return {
      user: JSON.parse(sessionStorage.getItem("user")),
      dialogVisible: false,
      form: {},
    }
  },

  methods: {
    load() {
      request.get("/user/"+phone).then(res => {
            console.log(res)
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "更新成功"
              })
            } else {
              this.$message({
                type: "error",
                message: "更新失败"
              })
            }
            this.user=res.data
          }

      )
    },
    updateInfo(phone) {

    },
    updatePass() {

    },
    handleEdit() {
      this.form = JSON.parse(sessionStorage.getItem("user"))//深拷贝
      this.dialogVisible = true
    },
    save() {
      if (this.form.phone) {
        //更新
        request.put("/user", this.form).then(res => {
              console.log(res)
              if (res.code === '0') {
                this.$message({
                  type: "success",
                  message: "更新成功"
                })
                sessionStorage.setItem("user", JSON.stringify(this.form))
                this.user=JSON.parse(sessionStorage.getItem("user"))
              } else {
                this.$message({
                  type: "error",
                  message: "更新失败"
                })
              }
            }
        )
        this.dialogVisible = false
      }



    },


  }
}
</script>
