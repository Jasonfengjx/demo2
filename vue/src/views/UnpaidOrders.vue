<template>
  <div style="padding: 10px 0 ">
    <el-scrollbar>
      <div class="flex-content">
        <el-table
            :data="tableData"
            style="width: 80vw"
            border
            stripe>
          <el-table-column
              prop="orderId"
              label="ID"
              sortable
          >
          </el-table-column>
          <el-table-column
              prop="orderState"
              label="订单状态"
          ></el-table-column>
          <el-table-column
              prop="orderType"
              label="订单类型"
          ></el-table-column>
          <el-table-column
              prop="orderTime"
              label="订单时间"
          ></el-table-column>
          <el-table-column
              prop="phone"
              label="电话"
          ></el-table-column>
          <el-table-column
              prop="passengerName"
              label="乘客姓名"
          ></el-table-column>
          <el-table-column
              prop="passengerIdentity"
              label="乘客身份证"
          ></el-table-column>
          <el-table-column
              prop="trainId"
              label="火车ID"
          ></el-table-column>
          <el-table-column
              prop="roomId"
              label="车厢ID"
          ></el-table-column>
          <el-table-column
              prop="seatId"
              label="座位ID"
          ></el-table-column>
          <el-table-column
              prop="price"
              label="价格"
          ></el-table-column>
          <el-table-column
              prop="trainStart"
              label="始发站"
          ></el-table-column>
          <el-table-column
              prop="trainEnd"
              label="终点站"
          ></el-table-column>
          <el-table-column
              prop="trainDate"
              label="运行时间"
          ></el-table-column>
          <el-table-column
              prop="trainStarttime"
              label="发车时间"
          ></el-table-column>
          <el-table-column
              prop="trainEndtime"
              label="到达时间"
          ></el-table-column>
          <el-table-column
              fixed="right"
              label="操作"
          >
            <template #default="scope">
              <el-popconfirm
                  title="确认支付吗？" @confirm="buy(scope.row)">
                <template #reference>
                  <el-button size="mini"  class="el-icon-shopping-cart-full">支付</el-button>
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
      </div>
    </el-scrollbar>


  </div>

</template>

<script>
// @ is an alias to /src

import request from "../utils/request";

export default {
  name: 'AllOrder',
  components: {},
  data() {
    return {
      form: {},
      dialogVisible: false,
      searchS: '黑河',
      searchE: '通北',
      trainId: '',
      trainNumber: '',
      datetime: '',
      tableData: [],
      user: {},
      currentPage: 1,
      pageSize: 10,
      total: 0,
    }
  },

  created() {
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)
    this.load()
  },
  methods: {
    searchForTickets() {

    },
    buy(row) {
      this.form = JSON.parse(JSON.stringify(row))//深拷贝
      request.post("/order/unpaid/" + this.user.phone,this.form
      ).then(res => {
        console.log(res)
        if (res.code === '0') {
          this.load()
          this.$message({
            type: "success",
            message: "支付成功"
          })
        } else {
          this.$message({
            type: "error",
            message: "支付失败"
          })
        }
      })
    },
    load() {
      request.get("/order/unpaid/" + this.user.phone, {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total=res.data.total
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
