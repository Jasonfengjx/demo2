<template>
  <div style="padding: 10px 0 ">
    <div style="margin: 10px 0" >
      <el-button type="primary" @click="add" class="el-icon-circle-plus-outline"> 新增</el-button>
      <el-button type="primary" class="el-icon-bottom-right"> 导出</el-button>
      <el-button type="primary" class="el-icon-upload2"> 导入</el-button>
      <el-button type="primary" class="el-icon-download"> 下载</el-button>
    </div>


    <div style="margin: 10px 0">
      <el-input
          v-model="search"
          placeholder="请输入关键字"
          clearable
          style="width: 20%"
      >
      </el-input>
      <el-button type="primary" style="margin-left: 5px" @click="this.load()" class="el-icon-search">查询</el-button>
    </div>
    <el-table
        :data="tableData"
        style="width: 80vw"
        border
        stripe
        >
      <el-table-column
          prop="phone"
          label="Phone"
          sortable
      >
      </el-table-column>
      <el-table-column
          prop="name"
          label="用户名"
      >
      </el-table-column>
      <el-table-column
          prop="address"
          label="地址"
      ></el-table-column>
      <el-table-column
          prop="age"
          label="年龄"
      ></el-table-column>
      <el-table-column
          prop="sex"
          label="性别"
      >
      </el-table-column>
      <el-table-column
          prop="email"
          label="邮箱"
      ></el-table-column>
      <el-table-column
          prop="identity"
          label="身份信息"
      ></el-table-column>
      <el-table-column
          label="角色"
      >
        <template #default="scope">
          <span v-if="scope.row.role === 1">管理员</span>
          <span v-if="scope.row.role === 2">普通用户</span>
        </template>
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
      >
        <template #default="scope">
          <el-button size="mini" @click="handleEdit(scope.row)" class="el-icon-paperclip">编辑</el-button>
          <el-popconfirm
              title="这是一段内容确定删除吗？" @confirm="handleDelete(scope.row.phone)"
          >
            <template #reference>
              <el-button size="mini" type="danger" class="el-icon-remove-outline">删除</el-button>
            </template>
          </el-popconfirm>

        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 10px 0">
      <span class="demonstration"></span>
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
    <el-dialog title="提示" v-model="dialogVisible" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="电话号码">
          <el-input v-model="form.phone" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="form.name" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input prefix-icon="el-icon-lock" v-model="form.password" show-password prop="password" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio v-model="form.sex" label="男">男</el-radio>
          <el-radio v-model="form.sex" label="女">女</el-radio>
          <el-radio v-model="form.sex" label="未知">未知</el-radio>
        </el-form-item>
        <el-form-item label="角色">
          <el-radio v-model="form.role" :label="1">管理员</el-radio>
          <el-radio v-model="form.role" :label="2">普通用户</el-radio>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="身份证">
          <el-input v-model="form.identity" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="form.age" style="width: 80%"></el-input>
        </el-form-item>


      </el-form>
      <template #footer>
      <span class="dialog-footer">
         <el-button @click="dialogVisible = false">取 消</el-button>
         <el-button type="primary" @click="save">确 定</el-button>
       </span>
      </template>
    </el-dialog>
  </div>

</template>

<script>
// @ is an alias to /src

import request from "../utils/request";

export default {
  name: 'Home',
  components: {},

  data() {
    return {
      user:sessionStorage.getItem("user"),
      form: {},
      dialogVisible: false,
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: []
    }
  },

  created() {
    this.load()
  },
  methods: {
    load() {

      request.get("/user", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    save() {
      //数据交互的API
      //axios
      if (this.form.phone) {
        //更新
        request.put("/user", this.form).then(res => {
              console.log(res)
              if (res.code === '0') {
                this.load()
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
            }
        )
        this.load()
        this.dialogVisible = false
      } else {
        //新增
        request.post("/user", this.form).then(res => {
              console.log(res)
              if (res.code === '0') {
                this.load(),
                this.$message({
                  type: "success",
                  message: "新增成功"
                })
              } else {
                this.$message({
                  type: "error",
                  message: "新增失败"
                })
              }
            }
        )
        this.load()
        this.dialogVisible = false
      }
    },
    add() {
      //打开一个弹窗
      this.dialogVisible = true
      this.form = {}

    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))//深拷贝
      this.dialogVisible = true
    },
    handleSizeChange(pageSize) {
      // 改变当前每页的个数时触发
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      //改变当前页码出发
      this.currentPage = pageNum
      this.load()
    },
    handleDelete(phone) {
      console.log(phone)
      request.delete("/user/" + phone).then(res => {
            console.log(res)
            if (res.code === '0') {
              this.load()
              this.$message({
                type: "success",
                message: "删除成功"
              })
            } else {
              this.$message({
                type: "error",
                message: "删除失败"
              })
            }
          }
      )
      this.load()
    },
  }


}
</script>
