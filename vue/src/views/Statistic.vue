<template>
  <div style="padding: 10px 0 ">

    <h2>统计表</h2>
    <el-table
        :data="tableData"
        style="width: 80vw"
        border
        stripe
    >
      <el-table-column
          prop="type"
          label="类型"
      >
      </el-table-column>
      <el-table-column
          prop="number"
          label="数量"
      ></el-table-column>
      <el-table-column
          prop="ratio"
          label="占比"
      ></el-table-column>
    </el-table>
    <div>
      <dv-charts :option="option"/>
    </div>
    <div style="background-color: white">
      <h2>柱状统计图</h2>
      <div id="chart_example">

      </div>
    </div>
    <div>
      <div style="background-color: white">
        <h2>饼统计图</h2>
        <div id="NanTing">

        </div>
      </div>
      <div style="background-color: white">
        <h2>油量表统计图</h2>
        <div id="Y1">

        </div>
        <div id="Y2">

        </div>
      </div>
    </div>
  </div>


</template>

<script>
import request from "../utils/request";
import * as echarts from 'echarts'

export default {
  name: 'Statistic',
  components: {},

  data() {
    return {
      user: sessionStorage.getItem("user"),
      form: {},
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      option: {
        title: {
          text: '剩余油量表',
          style: {
            fill: '#ffffff'
          }
        },
        series: [
          {
            type: 'gauge',
            data: [{name: 'itemA', value: 55}],
            center: ['50%', '55%'],
            axisLabel: {
              formatter: '{value}%',
              style: {
                fill: '#fff'
              }
            },
            axisTick: {
              style: {
                stroke: '#fff'
              }
            },
            animationCurve: 'easeInOutBack'
          }
        ]
      }
    }
  },

  created() {
    this.load()
  },
  mounted() {
  },
  methods: {
    load() {
      request.get("/order/Statistic", {
        params: {}
      }).then(res => {
        console.log(res)
        this.tableData = res.data
        console.log(res.data)
        let aList=[]
        let bList=[]
        for (let i = 0; i < 7; i++) {
          aList[i] = res.data[i].number
          bList[i] = {}

        }
        for (let i = 0; i < 6; i++) {
          bList[i].value = res.data[i].number
          bList[i].name = res.data[i].type
        }
        let myChart = echarts.init(document.getElementById('chart_example'));

        let option = {
          xAxis: {
            data: ['完成', '退票', '改签', '未支付', '未出行','取消', '总共']
          },
          yAxis: {},
          series: [
            {
              type: 'bar',
              data: [23, 24, 18, 25, 27, 28]
            }
          ]};
        option.series[0].data=aList
        console.log(option.series[0].data);
        myChart.setOption(option)


        let myChart1 = echarts.init(document.getElementById('NanTing'));
        let option1 = {
          title: {
            text: '订单信息统计',
            subtext: '订单数据',
            left: 'center'
          },
          tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
          },
          legend: {
            bottom: 10,
            left: 'center',
            data: ['西凉', '益州', '兖州', '荆州', '幽州']
          },
          series: [
            {
              type: 'pie',
              radius: '65%',
              center: ['50%', '50%'],
              selectedMode: 'single',
              data: [

              ],
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            }
          ]
        }
        option1.series[0].data=bList
        myChart1.setOption(option1)

        let myY1=echarts.init(document.getElementById('Y1'))
        let myY2=echarts.init(document.getElementById('Y2'))
        let optionY1= {
          tooltip: {
            formatter: '{a} <br/>{b} : {c}%'
          },
          series: [{
            name: 'Pressure',
            type: 'gauge',
            progress: {
              show: true
            },
            detail: {
              valueAnimation: true,
              formatter: '{value}'
            },
            data: [{
              value: 50,
              name: 'SCORE'
            }]
          }]
        }
        optionY1.series[0].data[0]=bList[0]
        myY1.setOption(optionY1)
        optionY1.series[0].data[0]=bList[1]
        myY2.setOption(optionY1)


      })

    },
    charset() {
      const container = document.getElementById('container')
      const myChart = new Charts(container)
    }


  }

}
</script>
<style>
#container {
  width: 100%;
  height: 500px;
  background: #f1f1f1;
}
#chart_example{
  width: 100%;
  height: 500px;
  border: 1px solid red;
  margin: 0 auto;
}
#NanTing{
  width: 100%;
  height: 500px;
  border: 1px solid red;
  margin: 0 auto;
}
#Y1{
  width: 50%;
  height: 500px;
  border: 1px solid red;
  margin: 0 auto;
}
#Y2{
  width: 50%;
  height: 500px;
  border: 1px solid red;
  margin: 0 auto;
}
</style>
