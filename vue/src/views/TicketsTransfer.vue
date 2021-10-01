<template>
  <div style="padding: 10px 0 ;margin: 0 auto">

    <el-scrollbar>
      <div class="flex-content">
        <el-table
            :data="tableData1"
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
              <el-button size="mini" type="danger" class="el-icon-star-on">预定</el-button>
              <el-popconfirm
                  title="这是一段内容确定购买吗？" @confirm="buy1(scope.$index,scope.row.seatType,scope.row.seatSum,scope.row.basicCost)"
              >
                <template #reference>
                  <el-button size="mini"  class="el-icon-shopping-cart-full">购买</el-button>

                </template>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
      </div>


      <div class="flex-content">
        <el-table
            :data="tableData2"
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
              <el-button size="mini" type="danger" class="el-icon-star-on">预定</el-button>

              <el-popconfirm
                  title="这是一段内容确定购买吗？" @confirm="buy2(scope.$index,scope.row.seatType,scope.row.seatSum,scope.row.basicCost)"
              >
                <template #reference>
                  <el-button size="mini"  class="el-icon-shopping-cart-full">购买</el-button>
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
      trainId1: '',
      trainId2: '',
      trainNumber: '',
      datetime: '',
      tableData1: [],
      tableData2: [],
      startStationNo:'',
      transferStationNo1:'',
      transferStationNo2:'',
      endStationNo:'',
      train:'',
    }
  },

  created() {
    this.trainId1 = this.$route.query.trainNo1
    this.trainId2 = this.$route.query.trainNo2
    this.startStationNo = this.$route.query.startStationNo
    this.transferStationNo1 = this.$route.query.transferStationNo1
    this.transferStationNo2 = this.$route.query.transferStationNo2
    this.endStationNo = this.$route.query.endStationNo
    this.datetime = this.$route.query.datetime
    this.load1()
    this.load2()
  },
  methods: {

    buy1(index,seatType, seatSum, price) {
      if (parseInt(seatSum) > 0) {
        let trainSTR = sessionStorage.getItem("traintransfer") || "{}"
        this.train = JSON.parse(trainSTR)
        this.$router.push(
            {
              path: "/book1",
              query: {
                trainId: this.train.trainNo1,
                trainNumber: this.train.trainNumber1,
                startNo: this.train.startStationNo,
                endNo: this.train.transferStationNo1,
                stationS: this.train.startStationName,
                stationE: this.train.transferStationName,
                seatType: seatType,
                price: price,
                datetime: this.datetime,
                startTime: this.train.startTime1,
                endTime: this.train.arriveTime1
              }
            }
        )
      } else {
        alert("您好，没有余票了，请选择其他种类的票")
      }
    },
    buy2(index,seatType, seatSum, price) {
      let trainSTR = sessionStorage.getItem("traintransfer") || "{}"
      this.train = JSON.parse(trainSTR)
      if (parseInt(seatSum) > 0) {

        this.$router.push(
            {
              path: "/book",
              query: {
                trainId: this.train.trainNo2,
                trainNumber: this.train.trainNumber2,
                startNo: this.train.transferStationNo2,
                endNo: this.train.endStationNo,
                stationS: this.train.transferStationName,
                stationE: this.train.endStationName,
                seatType: seatType,
                price: price,
                datetime: this.datetime,
                startTime: this.train.startRunningTime2,
                endTime: this.train.endRunningTime2
              }
            }
        )
      } else {
        alert("您好，没有余票了，请选择其他种类的票")
      }
    },
    load1() {

      request.get("/seat/" + this.trainId1, {
        params: {
          startStationNo:this.startStationNo,
          endStationNo:this.transferStationNo1
        }
      }).then(res => {
        console.log(res)
        this.tableData1 = res.data
      })
    },
    load2() {

      request.get("/seat/" + this.trainId2, {
        params: {
          startStationNo:this.transferStationNo2,
          endStationNo:this.endStationNo
        }
      }).then(res => {
        console.log(res)
        this.tableData2 = res.data
      })
    },


  }


}
</script>
