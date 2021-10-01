<template>
  <div style="display: flex" >
    <el-menu
        :default-active="user"
        default-openeds=""
        class="el-menu-vertical-demo"
        style="width: 200px;min-height: 100vh"
        router
        :style="bg"
    >
<!--:style="bg"-->

      <el-submenu index="1" v-if=" user.role === 1">
        <template #title>
          <i class="el-icon-s-tools"></i>
          <span>系统管理</span>
        </template>
        <el-menu-item index="user" class="el-icon-s-custom">   用户管理</el-menu-item>
        <el-menu-item index="station" class="el-icon-office-building">   站点管理</el-menu-item>
        <el-menu-item index="train" class="el-icon-suitcase">   车次管理</el-menu-item>
        <el-menu-item index="trainInfo" class="el-icon-school">   经停站管理</el-menu-item>
        <el-menu-item index="seat" class="el-icon-suitcase">   车票管理</el-menu-item>
        <el-menu-item index="tt" class="el-icon-data-board">   列车时间表管理</el-menu-item>
      </el-submenu>


      <el-submenu index="2" v-if=" user.role === 2">
        <template #title>
          <i class="el-icon-suitcase-1"></i><span>余票查询</span></template>
        <el-menu-item index="tt2" class="el-icon-info">   列车信息查询</el-menu-item>
        <el-menu-item index="direct" class="el-icon-caret-right">   直达车余票查询</el-menu-item>
        <el-menu-item index="transfer" class="el-icon-refresh">   换乘余票查询</el-menu-item>
      </el-submenu>
      <el-submenu index="3">

        <template #title>
          <i class="el-icon-user-solid"></i>
          <span>个人信息</span>
        </template>
        <el-menu-item index="selfInfo"  class="el-icon-info">   信息管理</el-menu-item>
        <el-menu-item index="passengerInfo" class="el-icon-user" v-if=" user.role === 2">   乘客信息</el-menu-item>
      </el-submenu>

      <el-submenu index="4" v-if=" user.role === 2">
        <template #title>
          <i class="el-icon-coin"></i>
          <span>订单管理</span>
        </template>
        <el-menu-item index="allOrders" class="el-icon-coin">  全部订单</el-menu-item>
        <el-menu-item index="unpaidOrders"   class="el-icon-coin">   未支付订单</el-menu-item>
        <el-menu-item index="unGoOrders"   class="el-icon-coin">    未出行订单</el-menu-item>
      </el-submenu>
      <el-submenu index="5" v-if=" user.role === 1">
        <template #title>
          <i class="el-icon-coin"></i>
          <span>统计</span>
        </template>
        <el-menu-item index="statistic" class="el-icon-coin">  统计一</el-menu-item>
      </el-submenu>


    </el-menu>
  </div>
</template>

<script>
import request from "../utils/request";

export default {
  name: "Aside",
  data() {
    return {
      user: {},
      path: this.$route.path,
      bg: {
        backgroundImage: "url(" + require("@/assets/Aside.jpg") + ")",
        backgroundRepeat: "no-repeat",
        backgroundSize: "100% 100%"
      }

    }
  },
  created() {
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)
    if (userStr.role == '1') {
      request.get("/user/" + this.user.id).then(res => {
        if (res.code === '0') {
          this.user = res.data
        }
      })
    }

  },
  methods: {
    methods: {
      handleOpen(key, keyPath) {
        console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        console.log(key, keyPath);
      }
    }
  }
}

</script>

<style scoped>

</style>