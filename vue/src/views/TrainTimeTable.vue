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
          placeholder="请输入起始站或终点站"
          clearable
          style="width: 20%"
      >
      </el-input>
      <el-button type="primary" style="margin-left: 5px" @click="this.load()"  class="el-icon-search">查询</el-button>

    </div>

    <el-scrollbar>
      <div class="flex-content">
        <el-table
            :data="tableData"
            style="width: 80vw"
            border
            stripe>
          <el-table-column
              prop="id"
              label="ID"
              sortable
          >
          </el-table-column>
          <el-table-column
              prop="trainId"
              label="火车ID"
          ></el-table-column>
          <el-table-column
              prop="trainNumber"
              label="火车车次"
          ></el-table-column>
          <el-table-column
              prop="departureStation"
              label="始发站"
          ></el-table-column>
          <el-table-column
              prop="endStation"
              label="终点站"
          > </el-table-column>
          <el-table-column
              prop="fromStation"
              label="上一站"
          ></el-table-column>
          <el-table-column
              prop="toStation"
              label="下一站"
          ></el-table-column>
          <el-table-column
              prop="departureTime"
              label="发车时间"
          ></el-table-column>
          <el-table-column
              prop="arrivalTime"
              label="到达时间"
          ></el-table-column>
          <el-table-column
              prop="useTime"
              label="花费时间"
          ></el-table-column>
          <el-table-column
              prop="businessSeat"
              label="商务座"
          ></el-table-column>
          <el-table-column
              prop="firstClassSeats"
              label="一等座"
          ></el-table-column>
          <el-table-column
              prop="secondClassSeats"
              label="二等座"
          ></el-table-column>
          <el-table-column
              prop="highGradeSoftBerth"
              label="高级软卧"
          ></el-table-column>
          <el-table-column
              prop="softBedFirstClassBed"
              label="一等软卧"
          ></el-table-column>
          <el-table-column
              prop="motorTrainBed"
              label="一等硬卧"
          ></el-table-column>
          <el-table-column
              prop="hardSecondClassBed"
              label="二等硬卧"
          ></el-table-column>
          <el-table-column
              prop="softSeats"
              label="软座"
          ></el-table-column>
          <el-table-column
              prop="hardSeats"
              label="硬座"
          ></el-table-column>
          <el-table-column
              prop="seatless"
              label="无座"
          ></el-table-column>
          <el-table-column
              fixed="right"
              label="操作"
          >
            <template #default="scope">
              <el-button size="mini" @click="handleEdit(scope.row)" class="el-icon-paperclip">编辑</el-button>
              <el-popconfirm
                  title="这是一段内容确定删除吗？" @confirm="handleDelete(scope.row.id)"
              >
                <template #reference>
                  <el-button size="mini" type="danger" class="el-icon-remove-outline">删除</el-button>
                </template>
              </el-popconfirm>

            </template>
          </el-table-column>
        </el-table>
      </div>
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
    </el-scrollbar>

    <el-dialog title="提示" v-model="dialogVisible" width="30%">
      <el-form :model="form" label-width="120px">
        <el-form-item label="火车ID">
          <el-input v-model="form.trainId" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="火车名">
          <el-input v-model="form.trainName" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="到达日">
          <el-radio v-model="form.arriveType" label="当日到达">当日到达</el-radio>
          <el-radio v-model="form.arriveType" label="次日到达">次日到达</el-radio>
          <el-radio v-model="form.arriveType" label="三日到达">三日到达</el-radio>
        </el-form-item>

        <el-form-item label="到达时间">
          <el-input v-model="form.arriveTime" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="发车时间">
          <el-input v-model="form.startTime" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="运行时间">
          <el-input v-model="form.spanTime" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="站台编号">
          <el-input v-model="form.stationNo" style="width: 80%"></el-input>
        </el-form-item>
        <el-form-item label="站台名称">
          <el-input v-model="form.stationName" style="width: 80%"></el-input>
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
      request.get("/ts", {
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
        request.put("/ts", this.form).then(res => {
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
        this.load()

      } else {
        //新增
        request.post("/ts", this.form).then(res => {
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
        this.load()

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
    handleDelete(trainId,station) {
      console.log(trainId)
      request.delete("/ts/" + trainId+"/"+station).then(res => {
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
