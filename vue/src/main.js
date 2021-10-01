import App from './App.vue'
import { createApp } from 'vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus';
import 'element-plus/lib/theme-chalk/index.css';
import '@/assets/css/global.css'
import dataV from '@jiaminghi/data-view'
import * as echarts from 'echarts'

createApp(App).use(store).use(router).use(ElementPlus).mount('#app').use(dataV)
app.config.globalProperties.$echarts = echarts
