<template>
  <div>
    <div>
      <el-steps :active="active" align-center style="margin-top: 10px">
        <el-step title="添加乘客"></el-step>
        <el-step title="选择座位"></el-step>
        <el-step title="付款"></el-step>
        <el-step title="订票成功"></el-step>
      </el-steps>
    </div>
    <div style="padding: 10px 0 ">
      <div style="margin: 10px 0">
        <el-button type="primary" @click="add" class="el-icon-circle-plus-outline"> 新增</el-button>
        <el-button type="primary" @click="selectSeats" class="el-icon-circle-plus-outline"> 选择座位</el-button>
        <el-button type="primary" @click="pay" class="el-icon-circle-plus-outline"> 去支付</el-button>
        <el-button type="primary" @click="buynext" class="el-icon-circle-plus-outline"> 继续购买</el-button>

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
            prop="seatId"
            label="座位号"
        ></el-table-column>
        <el-table-column
            fixed="right"
            label="操作"
        >
          <template #default="scope">
            <el-button size="mini" @click="handleEdit(scope.$index,scope.row)" class="el-icon-paperclip">选座</el-button>
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
      <!--      添加乘客-->
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
          <el-form-item label="用户号码">
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
      <!--      软卧-->

      <el-dialog title="提示" v-model="dialogVisible2" width="30%">
        <el-form :model="form" label-width="120px">
          <el-form-item label="选择座位">
            <el-radio v-model="form.seatId" label="上铺">上铺</el-radio>
            <el-radio v-model="form.seatId" label="下铺">下铺</el-radio>
          </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
         <el-button @click="OKAY">取 消</el-button>
         <el-button type="primary" @click="save1">确 定</el-button>
       </span>
        </template>
      </el-dialog>
      <!--      硬卧-->
      <el-dialog title="提示" v-model="dialogVisible3" width="30%">
        <el-form :model="form" label-width="120px">
          <el-form-item label="选择座位">
            <el-radio v-model="form.seatId" label="上铺">上铺</el-radio>
            <el-radio v-model="form.seatId" label="中铺">中铺</el-radio>
            <el-radio v-model="form.seatId" label="下铺">下铺</el-radio>
          </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
         <el-button @click="dialogVisible3 = false">取 消</el-button>
         <el-button type="primary" @click="save1">确 定</el-button>
       </span>
        </template>
      </el-dialog>
      <!--      特等座-->
      <el-dialog title="提示" v-model="dialogVisible4" width="30%">
        <el-form :model="form" label-width="120px">
          <el-form-item label="选择座位">
            <el-input v-model="form.seatId" style="width: 80%"></el-input>
            <el-radio v-model="form.seatId" label="A">A</el-radio>
            <el-radio v-model="form.seatId" label="B">B</el-radio>

          </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
         <el-button @click="dialogVisible4 = false">取 消</el-button>
         <el-button type="primary" @click="save1">确 定</el-button>
       </span>
        </template>
      </el-dialog>
      <!--      一等座-->
      <el-dialog title="提示" v-model="dialogVisible5" width="30%">
        <el-form :model="form" label-width="120px">
          <el-form-item label="选择座位">
            <el-radio v-model="form.seatId" label="A">A</el-radio>
            <el-radio v-model="form.seatId" label="B">B</el-radio>
            <el-radio v-model="form.seatId" label="C">C</el-radio>
          </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
         <el-button @click="dialogVisible5 = false">取 消</el-button>
         <el-button type="primary" @click="save1">确 定</el-button>
       </span>
        </template>
      </el-dialog>
      <!--      二等座-->
      <el-dialog title="提示" v-model="dialogVisible6" width="30%">
        <el-form :model="form" label-width="120px">
          <el-form-item label="选择座位">
            <el-input v-model="form.seatId" style="width: 80%"></el-input>
            <el-radio v-model="form.seatId" label="A">A</el-radio>
            <el-radio v-model="form.seatId" label="B">B</el-radio>
            <el-radio v-model="form.seatId" label="C">C</el-radio>
            <el-radio v-model="form.seatId" label="D">D</el-radio>
          </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
         <el-button @click="dialogVisible6 = false">取 消</el-button>
         <el-button type="primary" @click="save1">确 定</el-button>
       </span>
        </template>
      </el-dialog>
      <!--      硬座-->
      <el-dialog title="提示" v-model="dialogVisible7" width="30%">
        <el-form :model="form" label-width="120px">
          <el-form-item label="选择座位">
            <el-radio v-model="form.seatId" label="A">A</el-radio>
            <el-radio v-model="form.seatId" label="B">B</el-radio>
            <el-radio v-model="form.seatId" label="C">C</el-radio>
            <el-radio v-model="form.seatId" label="D">D</el-radio>
            <el-radio v-model="form.seatId" label="D">E</el-radio>
          </el-form-item>
        </el-form>

        <template #footer>
      <span class="dialog-footer">
         <el-button @click="dialogVisible7 = false">取 消</el-button>
         <el-button type="primary" @click="save1">确 定</el-button>
       </span>
        </template>
      </el-dialog>
    </div>
    <div>
      <div style="padding: 10px 0 ">
        <el-scrollbar>
          <div class="flex-content">
            <el-table
                :data="tableData2"
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
                  label="订单ID"
              ></el-table-column>
              <el-table-column
                  prop="orderType"
                  label="订单状态"
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
                      title="这是一段内容确定删除吗？" @confirm="buy(scope.row)">
                    <template #reference>
                      <el-button size="mini"  class="el-icon-shopping-cart-full">支付</el-button>
                    </template>
                  </el-popconfirm>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-scrollbar>
      </div>
    </div>
  </div>

</template>

<script>
import request from "@/utils/request";

export default {
  name: "Book",
  components: {},
  data() {
    return {
      active: 0,//通过改变这个变量的值来控制
      user: JSON.parse(sessionStorage.getItem("user")),
      form:
          {
            phone: '1234',
            trainId: '',
            price: '',
            trainStart: '',
            trainEnd: '',
            trainDate: '',
            trainStarttime: '',
            trainEndtime: '',
          },
      form2: {},
      dialogVisible: false,
      dialogVisible2: false,
      dialogVisible3: false,
      dialogVisible4: false,
      dialogVisible5: false,
      dialogVisible6: false,
      dialogVisible7: false,

      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      tableData2: [],
      price: '',
      datetime: '',
      trainStarttime: '',
      trainEndtime: '',
      seatType: '',
      trainId: '',
      trainNumber: '',
      startNo: '',
      endNo: '',
      stationS: '',
      stationE: '',
      index: 0,
    }
  },
  created() {
    this.seatType = this.$route.query.seatType
    this.trainId = this.$route.query.trainId
    this.trainNumber = this.$route.query.trainNumber
    this.startNo = this.$route.query.startNo
    this.endNo = this.$route.query.endNo
    this.stationS = this.$route.query.stationS
    this.stationE = this.$route.query.stationE
    this.price = this.$route.query.price
    this.datetime = this.$route.query.datetime
    this.trainStarttime = this.$route.query.startTime
    this.trainEndtime = this.$route.query.endTime
    this.load()
  },
  methods: {
    load2(){
      request.get("/order/unpaid/" + this.user.phone, {
        params: {
          pageNum2: this.currentPage2,
          pageSize2: this.pageSize2,
        }
      }).then(res => {
        console.log(res)
        this.tableData2 = res.data.records
      })
    },
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
      request.get("/order/unpaid/" + this.user.phone, {
        params: {
          pageNum2: this.currentPage2,
          pageSize2: this.pageSize2,
        }
      }).then(res => {
        console.log(res)
        this.tableData2 = res.data.records
      })
    },
    load1(){
      request.get("/order/unpaid/" + this.user.phone, {
        params: {
          pageNum2: this.currentPage2,
          pageSize2: this.pageSize2,
        }
      }).then(res => {
        console.log(res)
        this.tableData2 = res.data.records
      })
    },
    save1() {
      this.form.phone = this.user.phone
      this.form.trainId = this.trainId
      this.form.price = this.price
      this.form.trainStart = this.stationS
      this.form.trainEnd = this.stationE
      this.form.trainDate = this.datetime
      this.form.trainStarttime = this.trainStarttime
      this.form.trainEndtime = this.trainEndtime
      this.form.seatType = this.seatType

      request.put("/passenger/order", this.form).then(res => {
        if (res.code === '0') {
          this.load2()
          this.tableData[this.index].seatId = this.form.seatId
          this.dialogVisible2 = false
          this.$message({
            type: "success",
            message: "选座成功",
          })
        } else {
          this.dialogVisible2 = false
          this.$message({
            type: "error",
            message: "选座失败，请重新选座",
          })
        }
      })
      if (this.seatType === '软卧') {
        this.dialogVisible2 = false
      } else if (this.seatType === '硬卧') {
        this.dialogVisible3 = false
      } else if (this.seatType === '特等座') {
        this.dialogVisible4 = false
      } else if (this.seatType === '一等座') {
        this.dialogVisible5 = false
      } else if (this.seatType === '二等座') {
        this.dialogVisible6 = false
      } else if (this.seatType === '硬座') {
        this.dialogVisible7 = false
      }
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
        this.load()
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
      this.dialogVisible = true
      this.form = {}
    },
    handleEdit(index, row) {
      this.form = JSON.parse(JSON.stringify(row))//深拷贝
      if (this.seatType === '软卧') {
        this.dialogVisible2 = true
      } else if (this.seatType === '硬卧') {
        this.dialogVisible3 = true
      } else if (this.seatType === '特等座') {
        this.dialogVisible4 = true
      } else if (this.seatType === '一等座') {
        this.dialogVisible5 = true
      } else if (this.seatType === '二等座') {
        this.dialogVisible6 = true
      } else if (this.seatType === '硬座') {
        this.dialogVisible7 = true
      }

      this.form.phone = row.phone
      this.form.trainId = this.trainId
      //roomID没有解决
      this.form.roomId = '1'
      this.form.price = this.price
      this.form.trainStart = this.stationS
      this.form.trainEnd = this.stationE
      this.form.trainDate = this.datetime
      this.form.trainStarttime = this.trainStarttime
      this.form.trainEndtime = this.trainEndtime
      this.index = index
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
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
    selectSeats() {
      this.active = 1
    },
    pay() {
      this.active = 2
      this.buyAll()
    },
    finish() {
      this.active = 3
      this.$router.push("/unGoOrders")
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
    buyAll(){
      request.post("/order/unpaid/1/" + this.user.phone,this.form
      ).then(res => {
        console.log(res)
        if (res.code === '0') {
          this.load1()
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
    OKAY(){
      if (this.seatType === '软卧') {
        this.dialogVisible2 = false
      } else if (this.seatType === '硬卧') {
        this.dialogVisible3 = false
      } else if (this.seatType === '特等座') {
        this.dialogVisible4 = false
      } else if (this.seatType === '一等座') {
        this.dialogVisible5 = false
      } else if (this.seatType === '二等座') {
        this.dialogVisible6 = false
      } else if (this.seatType === '硬座') {
        this.dialogVisible7 = false
      }
    },
    buynext(){
      history.go(-1);
    }

  }
}

</script>

<style scoped>

</style>