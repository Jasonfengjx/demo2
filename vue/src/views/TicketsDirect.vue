<template>

  <!--  <div>-->
  <!--    <el-steps :active="active" align-center style="margin-top: 10px">-->
  <!--      <el-step title="添加乘客"></el-step>-->
  <!--      <el-step title="选择座位"></el-step>-->
  <!--      <el-step title="付款"></el-step>-->
  <!--      <el-step title="订票成功"></el-step>-->
  <!--    </el-steps>-->
  <!--  </div>-->

  <div style="padding: 10px 0 ">

    <el-scrollbar>
      <div class="flex-content">
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
              prop="seatType"
              label="座位类型"
          ></el-table-column>
          <el-table-column
              prop="seatSum"
              label="余票数量"
          ></el-table-column>
          <el-table-column
              prop="basicCost"
              label="价格"
          ></el-table-column>
          <el-table-column
              fixed="right"
              label="操作"
          >
            <template #default="scope">
              <el-button size="mini" type="danger" class="el-icon-star-on">收藏</el-button>
              <el-popconfirm
                  title="确认购买吗？" @confirm="buy1(scope.row.seatType,scope.row.seatSum,scope.row.basicCost)">
                <template #reference>
                  <el-button size="mini" @click="searchForTickets()" class="el-icon-shopping-cart-full">购买</el-button>
                </template>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <el-dialog title="提示" v-model="dialogVisible" width="30%">
        <el-form :model="form" label-width="120px">
          <el-form-item label="火车号">
            <el-input v-model="form.trainId" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="车厢号">
            <el-input v-model="form.roomId" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="座位类型">
            <el-input v-model="form.seatType" style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="购买数量">
            <el-input v-model="form.seatSum" style="width: 80%"></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
         <el-button @click="dialogVisible = false">取 消</el-button>
         <el-button type="primary" @click="save">确 定</el-button>
       </span>
        </template>
      </el-dialog>

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
      trainId: '',
      trainNumber: '',
      startNo: '',
      endNo: '',
      datetime: '',
      tableData: [],
      stationS: '',
      stationE: '',
      startTime: '',
      endTime: ''
    }
  },

  created() {
    this.trainId = this.$route.query.trainNo
    this.trainNumber = this.$route.query.trainNumber
    this.startNo = this.$route.query.startNo
    this.endNo = this.$route.query.endNo
    this.stationS = this.$route.query.stationS
    this.stationE = this.$route.query.stationE
    this.datetime = this.$route.query.datetime
    this.startTime = this.$route.query.startTime
    this.endTime = this.$route.query.endTime
    this.load()
  },
  methods: {
    searchForTickets(trainNo) {


    },
    buy1(seatType, seatSum, price) {
      console.log(this.datetime)
      console.log(seatType)
      if (parseInt(seatSum) > 0) {
        this.$router.push(
            {
              path: "/book",
              query: {
                trainId: this.trainId,
                trainNumber: this.trainNumber,
                startNo: this.startNo,
                endNo: this.endNo,
                stationS: this.stationS,
                stationE: this.stationE,
                seatType: seatType,
                price: price,
                datetime: this.datetime,
                startTime: this.startTime,
                endTime: this.endTime
              }
            }
        )
      } else {
        alert("您好，没有余票了，请选择其他种类的票")
      }

    },
    load() {
      request.get("/seat/" + this.trainId, {
        params: {
          startStationNo: this.startNo,
          endStationNo: this.endNo
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data
      })
    },

  }


}
</script>
