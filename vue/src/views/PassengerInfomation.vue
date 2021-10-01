<template>
  <div style="padding: 10px 0 ">
    <div style="margin: 10px 0" >
      <el-button type="primary" @click="add" class="el-icon-circle-plus-outline"> 新增</el-button>
      <el-button type="primary" class="el-icon-bottom-right"> 导出</el-button>
      <el-button type="primary" class="el-icon-upload2"> 导入</el-button>
      <el-button type="primary" class="el-icon-download"> 下载</el-button>
    </div>

    <el-table
        :data="tableData"
        style="width: 80vw"
        border
        stripe
    >
      <el-table-column
          prop="passengerName"
          label="乘客姓名"
          sortable
      >
      </el-table-column>
      <el-table-column
          prop="identityType"
          label="证件类型"
      >
      </el-table-column>
      <el-table-column
          prop="passengerIdentity"
          label="证件号码"
      >
      </el-table-column>
      <el-table-column
          prop="passengerType"
          label="乘客类型"
      ></el-table-column>
      <el-table-column
          prop="passengerPhone"
          label="乘客电话"
      ></el-table-column>
      <el-table-column
          prop="phone"
          label="用户电话"
      ></el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
      >
        <template #default="scope">
          <el-button size="mini" @click="handleEdit(scope.row)" class="el-icon-paperclip">编辑</el-button>
          <el-popconfirm
              title="这是一段内容确定删除吗？" @confirm="handleDelete(scope.row.passengerIdentity)"
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
        <el-form-item label="乘车人姓名">
          <el-input v-model="form.passengerName" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="证件类型">
          <el-radio v-model="form.identityType" label="居民身份证">居民身份证</el-radio>
          <el-radio v-model="form.identityType" label="居住证">居住证</el-radio>
          <el-radio v-model="form.identityType" label="侨胞">侨胞</el-radio>
        </el-form-item>
        <el-form-item label="证件号码">
          <el-input  v-model="form.passengerIdentity"  style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="乘客号码">
          <el-input v-model="form.passengerPhone" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="乘客类型">
          <el-radio v-model="form.passengerType" label="成年人">成人</el-radio>
          <el-radio v-model="form.passengerType" label="学生">学生</el-radio>
          <el-radio v-model="form.passengerType" label="儿童">儿童</el-radio>
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
import request from "../utils/request";

export default {
  name: 'Home',
  components: {},

  data() {
    return {
      user:sessionStorage.getItem("user"),
      form: { phone:'1234'},
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
      let user1=JSON.parse(sessionStorage.getItem("user"))
      request.get("/passenger/order", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          phone: user1.phone
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    save() {
      let user= JSON.parse(sessionStorage.getItem("user"))
      console.log(user.phone)
      this.form.phone=user.phone
      if (this.form.phone) {
        request.put("/passenger", this.form).then(res => {
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
        this.dialogVisible = false
      } else {
        request.post("/passenger", this.form).then(res => {
              console.log(res)
              if (res.code === '0') {
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
      request.delete("/passenger/" + phone).then(res => {
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
