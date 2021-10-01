<template>
  <div style="padding: 10px 0 ">
    <div style="margin: 10px 0">

     <div>
       <el-input
           v-model="search"
           placeholder="请输入车次"
           clearable
           style="width: 20%;margin:5px"

       >
       </el-input>
       <el-input
           v-model="searchS"
           placeholder="请输入始发站"
           clearable
           style="width: 10%;margin:5px"
       >
       </el-input>
       <el-input
           v-model="searchE"
           placeholder="请输入终点站"
           clearable
           style="width: 10%;margin:5px"
       >
       </el-input>

       <el-button type="primary"  round style="margin-left: 5px;margin:5px" @click="this.load()"  class="el-icon-search">查询1</el-button>
     </div>
      <div>
        <el-input
            v-model="search"
            placeholder="请输入车次"
            clearable
            style="width: 20%;margin:5px"

        >
        </el-input>
        <el-input
            v-model="searchL"
            placeholder="请输入上一站"
            clearable
            style="width: 10%;margin:5px"
        >
        </el-input>
        <el-input
            v-model="searchN"
            placeholder="请输入下一站"
            clearable
            style="width: 10%;margin:5px"
        >
        </el-input>

        <el-button type="success" round style="margin-left: 5px;margin:5px" @click="this.load2()"  class="el-icon-search">查询2</el-button>
      </div>


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
      searchS: '',
      searchE: '',
      searchL: '',
      searchN: '',
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
      request.get("/ts/a", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
          searchS: this.searchS,
          searchE: this.searchE
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    load2() {
      request.get("/ts/b", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
          searchS: this.searchS,
          searchE: this.searchE
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
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

  }


}
</script>
