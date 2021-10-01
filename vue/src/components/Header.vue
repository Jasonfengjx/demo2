<template>
  <div style=" height: 60px; line-height: 50px; border-bottom:1px solid;display: flex" :style="bg">
<!--    :style="bg"-->
    <div style="width: 500px ;margin-left: 30px; ">
      <i class="el-icon-files" style="font-size: 30px;color: #3190e8">
      </i>
      <span style=" font-size: 30px;font-weight:bold; color: #02b5fc" >     火 车 售 票 系 统   </span>
    </div>
    <div style="flex:1"></div>
    <div style="width: 100px">
      <el-dropdown>
          <span class="el-dropdown-link">
            <i class="el-icon-arrow-down el-icon--right">{{ user.name }}</i>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="$router.push('/selfInfo')">
              <i class="el-icon-s-custom"></i>
              <span> 信 息 </span>
            </el-dropdown-item>
            <el-dropdown-item @click="$router.push('/login')" class="el-icon-d-arrow-left"> 退出系统 </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      user: {},
      bg: {
        backgroundImage: "url(" + require("@/assets/Header.jpg") + ")",
        backgroundRepeat: "no-repeat",
        backgroundSize: "100% 100%"
      }

    }
  },
  created() {
    let str = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(str)
  }
}
</script>

<style scoped>

</style>