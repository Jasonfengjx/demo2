<template>
  <div>
    <Header/>
    <!--    主体-->
    <div style="display: flex">
      <!--      侧边栏-->
      <Aside/>
      <!--内容区域-->
      <div style="display: flex" :style="bg">
<!--        :style="bg"-->
        <router-view style="flex:1" />
      </div>

    </div>
  </div>
</template>


<script>
import Header from "@/components/Header";
import Aside from "@/components/Aside";

export default {
  name: "Layout",
  components: {
    Header,
    Aside
  },
  data(){
    return{
      bg: {
        backgroundImage: "url(" + require("@/assets/Body.jpg") + ")",
        backgroundRepeat: "no-repeat",
        backgroundSize: "100% 100%"
      }
    }
  }
}
</script>
<style scoped>


</style>

