import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout'
import Home from '../views/User'

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: 'selfInfo2',
    children: [
      {
        path: 'user',
        name: 'User',
        component: () => import("@/views/User"),//导入组件的方法 要学会
      },{
        path: 'selfInfo',
        name: 'SelfInfo',
        component: () => import("@/views/SelfInfomation"),//导入组件的方法 要学会
      },
      {
        path: 'passengerInfo',
        name: 'PassengerInfo',
        component: () => import("@/views/PassengerInfomation"),//导入组件的方法 要学会
      },{
        path: 'station',
        name: 'Station',
        component:() =>import("@/views/Station")
      },
      {
        path: 'train',
        name: 'Train',
        component:() =>import("@/views/Train")
      },
      {
        path: 'trainInfo',
        name: 'TrainInfo',
        component:() =>import("@/views/TrainInfo")
      },
      {
        path: 'seat',
        name: 'Seat',
        component:() =>import("@/views/Seat")
      },
      {
        path: 'tt',
        name: 'TrainTimeTable',
        component:() =>import("@/views/TrainTimeTable")
      },
      {
        path: 'tt2',
        name: 'TrainTimeTableUser',
        component:() =>import("@/views/TrainTimeTableUser")
      },
      {
        path: 'direct',
        name: 'TrainDirect',
        component:() =>import("@/views/TrainDirect")
      },
      {
        path: 'transfer',
        name: 'TrainTransfer',
        component:() =>import("@/views/TrainTransfer")
      },
      {
        path: '/selfInfo2',
        name: 'SelfInfo2',
        component: () => import("@/views/SelfInfomation"),//导入组件的方法 要学会
      },
      {
        path: '/allOrders',
        name: 'AllOrders',
        component: () => import("@/views/AllOrders"),//导入组件的方法 要学会
      },
      {
        path: '/unpaidOrders',
        name: 'UppaidOrders',
        component: () => import("@/views/UnpaidOrders"),//导入组件的方法 要学会
      },
      {
        path: '/unGoOrders',
        name: 'UnGoOrders',
        component: () => import("@/views/UnGoOrders"),//导入组件的方法 要学会
      },
      {
        path: '/TicketsDirect',
        name: 'TicketsDirect',
        component: ()=> import("@/views/TicketsDirect"),
      },
      {
        path: '/TicketsTransfer',
        name: 'TicketsTransfer',
        component:() =>import("@/views/TicketsTransfer")
      },
      {
        path: '/book',
        name: 'Book',
        component:() =>import("@/views/Book")
      },
      {
        path: '/statistic',
        name: 'Statistic',
        component:() =>import("@/views/Statistic")
      },
      {
        path: '/book1',
        name: 'Book1',
        component:() =>import("@/views/Book1")
      },
      {
        path: '/welcome',
        name: 'Welcome',
        component:() =>import("@/views/Welcome")
      }


    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: ()=> import("@/views/Login"),
  },
  {
    path: '/register',
    name: 'Register',
    component: ()=> import("@/views/Register"),
  },



]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
