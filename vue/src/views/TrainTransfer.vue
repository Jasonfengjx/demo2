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
            <el-button type="primary" round style="margin-left: 5px;margin:5px" @click="this.load() "
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
              prop="trainNo1"
              label="ID1"
              sortable
          >
          </el-table-column>
          <el-table-column
              prop="startStationName"
              label="起点站名称"
          ></el-table-column>
          <el-table-column
              prop="trainNumber1"
              label="火车车次"
              sortable
          ></el-table-column>
          <el-table-column
              prop="startTime1"
              label="发车时间"
          ></el-table-column>
          <el-table-column
              prop="arriveTime1"
              label="到达时间"
          ></el-table-column>
          <el-table-column
              prop="transferStationName"
              label="中转站名称"
          ></el-table-column>
          <el-table-column
              prop="trainNo2"
              label="ID2"
              sortable
          >
          </el-table-column>
          <el-table-column
              prop="trainNumber2"
              label="火车车次"
              sortable
          ></el-table-column>
          <el-table-column
              prop="startTime2"
              label="发车时间"
          >
          </el-table-column>
          <el-table-column
              prop="arriveTime2"
              label="到达时间"
          ></el-table-column>
          <el-table-column
              prop="endStationName"
              label="终点站名称"
          ></el-table-column>
          <el-table-column
              prop="endRunningTime1"
              label="运行时间1"
          ></el-table-column>
          <el-table-column
              prop="endRunningTime2"
              label="运行时间2"
          ></el-table-column>
          <el-table-column
              fixed="right"
              label="操作"
          >
            <template #default="scope">
              <el-button size="mini" @click="searchForTickets(scope.$index,scope.row.trainNo1,scope.row.trainNo2,
              scope.row.startStationNo,scope.row.transferStationNo1,
              scope.row.transferStationNo2,scope.row.endStationNo)"
                         class="el-icon-s-help">查看余票
              </el-button>
              <el-popconfirm
                  title="这是一段内容确定删除吗？" @confirm=""
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
  name: 'TrainTransfer',
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
    load() {
      request.get("/trainsearch/transfer", {
        params: {
          searchS: this.searchS,
          searchE: this.searchE,

        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data
      })
    },
    loadDatetime(datetime) {
      this.datetime=datetime
      request.get("/trainsearch/transfer", {
        params: {
          searchS: this.searchS,
          searchE: this.searchE,

        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data
      })
    },

    searchForTickets(index,trainNo1, trainNo2,startStationNo,transferStationNo1,transferStationNo2,endStationNo) {
      sessionStorage.setItem("traintransfer", JSON.stringify(this.tableData[index]))
      this.$router.push(
          {
            path: '/TicketsTransfer',
            query: {
              trainNo1: trainNo1,
              trainNo2: trainNo2,
              startStationNo:startStationNo,
              transferStationNo1:transferStationNo1,
              transferStationNo2:transferStationNo2,
              endStationNo:endStationNo,
              datetime:this.datetime
            }
          }
      )
    },
  }


}
</script>
