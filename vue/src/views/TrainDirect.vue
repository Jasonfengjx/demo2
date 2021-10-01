<template>
  <div style="padding: 10px 0 ">
    <div style="margin: 10px 0">


      <el-row :gutter="20">
        <el-col :span="6">
          <div>
            <el-input
                v-model="searchS"
                placeholder="请输入起点"
                clearable
                style="width: 100%;margin:5px"
            >
            </el-input>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <el-input
                v-model="searchE"
                placeholder="请输入终点"
                clearable
                style="width: 100%;margin:5px"
            ></el-input>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="block">
            <el-date-picker
                v-model="datetime"
                type="date"
                placeholder="选择日期"
                style="width: 100%;margin:5px"
            >
            </el-date-picker>
          </div>
        </el-col>
        <el-col :span="6">
          <div>
            <el-button type="primary" round style="margin-left: 5px;margin:5px" @click="this.load()"
                       class="el-icon-search">查询
            </el-button>
          </div>
        </el-col>
      </el-row>
    </div>
    <el-scrollbar>
      <div class="flex-content">
        <el-table
            :data="tableData"
            style="width: 80vw"
            border
            stripe>
          <el-table-column
              prop="trainNo"
              label="ID"
              sortable
          >
          </el-table-column>
          <el-table-column
              prop="trainNumber"
              label="火车车次"
              sortable
          ></el-table-column>
          <el-table-column
              prop="startTime"
              label="发车时间"
          ></el-table-column>
          <el-table-column
              prop="startStation"
              label="始发站"
          ></el-table-column>
          <el-table-column
              prop="endStation"
              label="终点站"
          ></el-table-column>
          <el-table-column
              prop="arriveTime"
              label="到达时间"
          ></el-table-column>
          <el-table-column
              prop="endRunningTime"
              label="运行时间"
          ></el-table-column>
          <el-table-column
              fixed="right"
              label="操作"
          >
            <template #default="scope">
              <el-button size="mini"
                         @click="searchForTickets(scope.row.trainNo,scope.row.trainNumber,
                         scope.row.startNo,scope.row.endNo,scope.row.startStation,
                         scope.row.endStation,scope.row.startTime,scope.row.arriveTime) "
                         class="el-icon-s-help">查看余票
              </el-button>
              <el-popconfirm
                  title="这是一段内容确定删除吗？" @confirm="buy()"
              >
                <template #reference>
                  <el-button size="mini" type="danger" class="el-icon-star-on">预定</el-button>
                </template>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
      </div>


    </el-scrollbar>


  </div>

</template>

<script>
// @ is an alias to /src

import request from "../utils/request";

export default {
  name: 'TrainDirect',
  components: {},

  data() {
    return {
      form: {},
      dialogVisible: false,
      searchS: '',
      searchE: '',
      datetime: '',
      tableData: [],
    }
  },

  created() {
    this.load()
  },
  methods: {
    searchForTickets(trainNo, trainNumber, startNo, endNo, stationS, stationE,startTime,endTime) {

      if (this.datetime === null) {
        alert("请输入时间")
      }
      else {
        console.log("trainDirect"+this.datetime)
        this.$router.push(
            {
              path: '/TicketsDirect',
              query: {
                trainNo: trainNo,
                trainNumber: trainNumber,
                startNo: startNo,
                endNo: endNo,
                stationS: stationS,
                stationE: stationE,
                datetime:this.datetime,
                startTime:startTime,
                endTime:endTime
              }
            }
        )
      }
    },
    buy() {

    },


    load() {
      if (this.datetime === null) {
        alert("请输入日期")
      } else {
        request.get("/trainsearch/direct", {
          params: {
            searchS: this.searchS,
            searchE: this.searchE,
          }
        }).then(res => {
          console.log(res)
          this.tableData = res.data
        })
      }

    },
    // load2() {
    //   request.get("/ts/b", {
    //     params: {
    //       pageNum: this.currentPage,
    //       pageSize: this.pageSize,
    //       search: this.search,
    //       searchS: this.searchS,
    //       searchE: this.searchE
    //     }
    //   }).then(res => {
    //     console.log(res)
    //     this.tableData = res.data.records
    //     this.total = res.data.total
    //   })
    // },


    // handleSizeChange(pageSize) {
    //   // 改变当前每页的个数时触发
    //   this.pageSize = pageSize
    //   this.load()
    // },
    // handleCurrentChange(pageNum) {
    //   //改变当前页码出发
    //   this.currentPage = pageNum
    //   this.load()
    // },

  }


}
</script>
