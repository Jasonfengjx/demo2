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
          placeholder="请输入起点城市或终止城市"
          clearable
          style="width: 20%"
      >
      </el-input>
      <el-button type="primary" style="margin-left: 5px" @click="this.load()"  class="el-icon-search">查询</el-button>

    </div>
    <el-table
        :data="tableData"
        style="width: 80vw"
        border
        stripe>
      <el-table-column
          prop="trainId"
          label="ID"
          sortable
      >
      </el-table-column>
      <el-table-column
          prop="trainType"
          label="火车类型"
      >
      </el-table-column>
      <el-table-column
          prop="trainRoom"
          label="车厢数"
      ></el-table-column>
      <el-table-column
          prop="trainStart"
          label="起点城市"
      ></el-table-column>
      <el-table-column
          prop="trainEnd"
          label="终点城市"
      > </el-table-column>
        <el-table-column
          prop="trainStarttime"
          label="发车时间"
      ></el-table-column>
      <el-table-column
          prop="trainEndtime"
          label="到站时间"
      >
      </el-table-column>
        <el-table-column
            prop="trainName"
            label="火车名"
        >
        </el-table-column>
      <el-table-column
          prop="arriveType"
          label="到达类型"
      >

      </el-table-column>
      <el-table-column
          prop="runningTime"
          label="运行时间"
      >
      </el-table-column>
      <el-table-column
          prop="state"
          label="状态"
      >
      </el-table-column>



      <el-table-column
          fixed="right"
          label="操作"
      >
        <template #default="scope">
          <el-button size="mini" @click="handleEdit(scope.row)" class="el-icon-paperclip">编辑</el-button>
          <el-popconfirm
              title="这是一段内容确定删除吗？" @confirm="handleDelete(scope.row.trainId)"
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
        <el-form-item label="火车ID">
          <el-input v-model="form.trainId" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="火车类型">
          <el-input v-model="form.trainType" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="车厢数">
          <el-input v-model="form.trainRoom" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="起始地">
          <el-input v-model="form.trainStart" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="终止地">
          <el-input v-model="form.TrainEnd" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="发车时间">
          <el-input v-model="form.TrainStarttime" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="到达时间">
          <el-input v-model="form.TrainEndtime" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="列车名">
          <el-input v-model="form.trainName" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="到达日">
          <el-radio v-model="form.arriveType" label="当日到达">当日到达</el-radio>
          <el-radio v-model="form.arriveType" label="次日到达">次日到达</el-radio>
          <el-radio v-model="form.arriveType" label="三日到达">三日到达</el-radio>
        </el-form-item>
        <el-form-item label="状态">
          <el-radio v-model="form.state" label="当日到达">运行中</el-radio>
          <el-radio v-model="form.state" label="次日到达">异常状态</el-radio>
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
      request.get("/train", {
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
      if (this.form.trainId) {
        //更新
        request.put("/train", this.form).then(res => {
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
        //新增
        request.post("/train", this.form).then(res => {
              console.log(res)
              if (res.code === '0') {
                this.load()
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
    handleDelete(trainId) {
      console.log(trainId)
      request.delete("/train/" + trainId).then(res => {
            console.log(res)
            if (res.code === '0') {
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
