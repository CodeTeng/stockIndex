<template>
  <div class="home">
    <div class="module-group">
      <div class="module">
        <div class="module-title">国内指数</div>
        <Table
            :tableData="domesticTableData"
            :tableColumnData="domesticTableColumnData"
        />
      </div>
      <div class="module">
        <div class="module-title">板块指数</div>
        <Table
            :tableData="plateTableData"
            :tableColumnData="plateTableColumnData"
        />
      </div>
      <div class="module mr0">
        <div class="module-title">外盘指数</div>
        <Table
            :tableData="outerPlateTableData"
            :tableColumnData="outerPlateTableColumnData"
        />
      </div>
    </div>
    <div class="module-group">
      <div class="module flex2">
        <div class="module-title">涨幅榜</div>
        <router-link :to="{path: '/stockList'}">
          <div class="module-title module-title-right">查看更多</div>
        </router-link>
        <Table
            :tableData="increaseTableData"
            :tableColumnData="increaseTableColumnData"
            @columnClickCallBack="callBack"
        />
      </div>
      <div class="module mr0">
        <div class="module-title">资讯新闻</div>
        <ul class="news-list">
          <li
              v-for="(item,index) in newsTableData"
              :key="index"
          >
            <div class="title">
              {{ item.title }}
            </div>
            <div class="content">
              {{ item.content }}
            </div>
            <div class="time">
              {{ item.time }}
            </div>
          </li>
        </ul>
      </div>
    </div>
    <div class="module-group">
      <div class="module mb0">
        <div
            ref="UdChart"
            :style="{width: '100%', height: '100%'}"
        ></div>
      </div>
      <div class="module mb0">
        <div
            ref="moneyChart"
            :style="{width: '100%', height: '100%'}"
        ></div>
        <div class="codekd">
          <div class="left">9:30</div>
          <div class="middle">11:30/13:00</div>
          <div class="right">15:00</div>
        </div>
      </div>
      <div class="module mr0 mb0">
        <div
            ref="meStockChart"
            :style="{width: '100%', height: '100%'}"
        ></div>
      </div>
    </div>
  </div>
</template>

<script>
import Table from "./table.vue";
import {
  quotIndexAll,
  quotSectorAll,
  quotExternalIndex,
  quotStockIncrease,
  quotStockUpdownCount,
  quotStockTradevol,
  quotStockUpdown
} from "@/api/stock/home";

export default {
  name: "home",
  components: {
    Table
  },
  data() {
    return {
      testData: "20211231",
      //国内指数
      domesticTableData: [],
      domesticTableColumnData: [
        {
          prop: "code",
          label: "大盘代码",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: ""
        },
        {
          prop: "name",
          label: "名称",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: ""
        },
        {
          prop: "tradePrice",
          label: "最新价",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: "0"
        },
        {
          prop: "upDown",
          label: "涨幅",
          isDifferentStates: true,
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: "0"
        },
        {
          prop: "tradeAmt",
          label: "总金额",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: "0"
        },
        {
          prop: "preClosePrice",
          label: "前收盘价",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: "0"
        },
        {
          prop: "openPrice",
          label: "开盘价",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: "0"
        },
        {
          prop: "tradeVol",
          label: "总手",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: "0"
        },
        {
          prop: "curDate",
          label: "当前日期",
          isDifferentStates: true,
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: "0"
        }
      ],
      //板块指数
      plateTableData: [],
      plateTableColumnData: [
        {
          prop: "code",
          label: "代码",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: ""
        },
        {
          prop: "name",
          label: "名称",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: ""
        },
        {
          prop: "avgPrice",
          label: "平均价",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: ""
        },
        {
          prop: "companyNum",
          label: "公司数量",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: ""
        },
        {
          prop: "updownRate",
          label: "涨幅",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: ""
        },
        {
          prop: "tradeAmt",
          label: "成交量",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: ""
        },
        {
          prop: "tradeVol",
          label: "成交金额",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: ""
        },
        {
          prop: "curDate",
          label: "当前日期",
          isDifferentStates: true,
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: "0"
        }
      ],
      //外盘指数
      outerPlateTableData: [],
      outerPlateTableColumnData: [
        {
          prop: "name",
          label: "名称",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: ""
        },
        {
          prop: "curPoint",
          label: "当前点",
          isDifferentStates: true,
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: ""
        },
        {
          prop: "upDownPoint",
          label: "涨跌点",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: ""
        },
        {
          prop: "upDownRate",
          label: "涨跌幅",
          isDifferentStates: true,
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: ""
        },
        {
          prop: "curTime",
          label: "时间",
          isDifferentStates: true,
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: ""
        }
      ],
      //涨幅榜
      increaseTableData: [],
      increaseTableColumnData: [
        {
          prop: "code",
          label: "代码",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: ""
        },
        {
          prop: "name",
          label: "名称",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: ""
        },
        {
          prop: "increase",
          label: "涨幅%",
          isDifferentStates: true,
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: ""
        },
        {
          prop: "tradePrice",
          label: "现价",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: ""
        },
        {
          prop: "upDown",
          label: "涨跌",
          isDifferentStates: true,
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: ""
        },
        {
          prop: "tradeVol",
          label: "总手",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: ""
        },
        {
          prop: "amplitude",
          label: "振幅",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: ""
        },
        {
          prop: "preClosePrice",
          label: "前收盘价",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: ""
        },
        {
          prop: "tradeAmt",
          label: "成交额",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: ""
        },
        {
          prop: "curDate",
          label: "当前日期",
          isDifferentStates: false,
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: "0"
        }
      ],
      //资讯新闻
      newsTableData: [
        {
          title: "东方财富",
          content: "A股三大指数收涨：民航与券商板块领涨 北向资金净流入近60亿",
          time: "15:00"
        },
        {
          title: "东方财富",
          content: "机构论市：新热点有望吹响冲锋号 沪指反弹目标位曝光",
          time: "15:53"
        },
        {
          title: "东方财富",
          content: "大逆转！巴菲特割肉割错了？美股A股航空板块联袂大涨",
          time: "12:32"
        },
        {
          title: "东方财富",
          content:
              "重磅政策支持湖北自贸区发展！涉及数字商务新模式新业态、电商大数据等",
          time: "00:46"
        },
        {
          title: "东方财富",
          content: "机构论市：6月投资主线已明朗 当下行情关注三大要点",
          time: "15:39"
        },
        {
          title: "东方财富",
          content: "地摊经济概念股发烫 多家上市公司紧急提示风险",
          time: "07:42"
        }
      ],
      //涨停跌停数
      UdChart: null,
      //赚钱效应
      moneyChart: null,
      //个股涨跌
      meStockChart: null,
      //国内指数定时器
      quotIndexAllTimer: null,
      //板块指数定时器
      quotSectorAllTimer: null,
      //外盘指数定时器
      quotExternalIndexTimer: null,
      //涨幅榜定时器
      quotStockIncreaseTimer: null,
      //涨停跌停定时器
      quotStockUpdownCountTimer: null,
      //个股涨跌定时器
      quotStockUpdownTimer: null,
      //成交量
      quotStockTradevolTimer: null
    };
  },
  methods: {
    //涨停跌停数
    UdChartDrawLine() {
      // 基于准备好的dom，初始化echarts实例
      this.UdChart = this.$echarts.init(this.$refs.UdChart);
      // 绘制图表
      this.UdChart.setOption({
        title: {
          text: "涨停跌停数",
          textStyle: {
            color: "#FFFFFF"
          }
        },
        xAxis: {
          type: "category",
          data: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
          axisLine: {
            show: true,
            lineStyle: {
              color: "#979797"
            }
          }
        },
        grid: {
          left: "10%",
          right: "10%",
          top: "20%",
          bottom: "10%"
        },
        yAxis: {
          type: "value",
          splitLine: {
            show: true,
            lineStyle: {
              color: ["#979797"],
              width: 1,
              type: "dashed"
            }
          },
          axisLine: {
            show: false
          }
        },
        tooltip: {
          show: true
        },
        legend: {
          data: ["涨停", "跌停"],
          right: "60px",
          top: "0px",
          inactiveColor: "#ffffff",
          textStyle: {
            color: "#ffffff"
          }
        },
        series: [
          {
            name: "涨停",
            data: [820, 932, 901, 934, 1290, 1330, 1320],
            type: "line",
            smooth: true,
            lineStyle: {
              color: "#FE1919"
            }
          },
          {
            name: "跌停",
            data: [82, 93, 91, 94, 190, 130, 320],
            type: "line",
            smooth: true,
            lineStyle: {
              color: "#249900"
            }
          }
        ],
        textStyle: {
          color: "#ffffff"
        }
      });
    },
    //成交量
    moneyChartDrawLine() {
      // 基于准备好的dom，初始化echarts实例
      this.moneyChart = this.$echarts.init(this.$refs.moneyChart);
      // 绘制图表
      this.moneyChart.setOption({
        title: {
          text: "成交量",
          textStyle: {
            color: "#FFFFFF"
          }
        },
        grid: {
          left: "10%",
          right: "10%",
          top: "20%",
          bottom: "10%"
        },
        xAxis: {
          show: false,
          type: "category",
          data: new Array(240),
          axisLine: {
            show: true,
            lineStyle: {
              color: "#979797"
            }
          }
        },
        yAxis: {
          type: "value",
          splitLine: {
            show: true,
            lineStyle: {
              color: ["#979797"],
              width: 1,
              type: "dashed"
            }
          },
          axisLine: {
            show: false
          }
        },
        tooltip: {
          show: true,
          trigger: "axis",
          formatter(params) {
            // debugger;
            return `<span>时间</span>: ${params[0].data.time}<br/>
            昨日: <span style="color: #FE1919">${params[0].value}</span><br/>
            今日: <span style="color: #249900">${params[1].value}</span><br/>`;
          }
        },
        legend: {
          data: ["昨日", "今日"],
          right: "80px",
          inactiveColor: "#ffffff",
          textStyle: {
            color: "#ffffff"
          }
        },
        series: [
          {
            name: "昨日",
            data: [820, 932, 901, 934, 1290, 1330, 1320],
            type: "line",
            smooth: true,
            lineStyle: {
              color: "#FE1919"
            }
          },
          {
            name: "今日",
            data: [82, 93, 91, 94, 190, 130, 320],
            type: "line",
            smooth: true,
            lineStyle: {
              color: "#249900"
            }
          }
        ],
        textStyle: {
          color: "#ffffff"
        }
      });
    },
    //个股涨跌
    meStockChartDrawLine() {
      // 基于准备好的dom，初始化echarts实例
      this.meStockChart = this.$echarts.init(this.$refs.meStockChart);
      // 绘制图表
      this.meStockChart.setOption({
        title: {
          text: "个股涨跌",
          textStyle: {
            color: "#FFFFFF"
          }
        },
        grid: {
          left: "10%",
          right: "10%",
          top: "20%",
          bottom: "15%"
        },
        xAxis: {
          type: "category",
          data: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
          axisLine: {
            show: true,
            lineStyle: {
              color: "#979797"
            }
          },
          axisLabel: {
            interval: 0,
            rotate: 38
          }
        },
        yAxis: {
          type: "value",
          splitLine: {
            show: true,
            lineStyle: {
              color: ["#979797"],
              width: 1,
              type: "dashed"
            }
          },
          axisLine: {
            show: false
          }
        },
        tooltip: {
          show: true
        },
        series: [
          {
            data: [820, 932, 901, 934, 1290, 1330, 1320],
            type: "bar",
            smooth: true,
            itemStyle: {
              color: function (value) {
                if (value.data.status == "up") {
                  return "#FE1919";
                } else {
                  return "#249900";
                }
              }
            },
            barMaxWidth: "10px"
          }
        ],
        textStyle: {
          color: "#ffffff"
        }
      });
    },
    //分钟换算
    formatSeconds(time) {
      return time * 60 * 1000;
    },
    //echarts自适应宽度
    echartsResize() {
      let that = this;
      window.addEventListener("resize", () => {
        that.UdChart.resize();
        that.moneyChart.resize();
        that.meStockChart.resize();
      });
    },
    //国内指数
    quotIndexAll() {
      let that = this;
      quotIndexAll().then(res => {
        console.log("国内指数信息：", res);
        // that.domesticTableData = data.items;
        if (res.data.code == 1) {
          that.domesticTableData = res.data.data;
        } else {
          that.domesticTableData = [];
        }
        //that.domesticTableData = res.data.data;
        that.quotIndexAllTimer = setInterval(() => {
          console.log("正在请求");
          that.quotIndexAll();
          clearInterval(that.quotIndexAllTimer);
          console.log("请求结束");
        }, that.formatSeconds(1));
      });
    },
    //板块指数
    quotSectorAll() {
      let that = this;
      quotSectorAll().then(res => {
        console.log("数据返回成功");
        console.log(res);
        // that.plateTableData = data.items;
        that.plateTableData = res.data.data;
        that.quotSectorAllTimer = setInterval(() => {
          console.log("正在请求");
          that.quotSectorAll();
          clearInterval(that.quotSectorAllTimer);
          console.log("请求结束", that.quotSectorAllTimer);
        }, that.formatSeconds(1));
      });
    },
    //外盘指数
    quotExternalIndex() {
      let that = this;
      quotExternalIndex().then(res => {
        console.log("数据返回成功");
        console.log(res);
        // that.outerPlateTableData = data.items;
        that.outerPlateTableData = res.data.data;
        that.quotExternalIndexTimer = setInterval(() => {
          console.log("正在请求");
          that.quotExternalIndex();
          clearInterval(that.quotExternalIndexTimer);
          console.log("请求结束", that.quotExternalIndexTimer);
        }, that.formatSeconds(1));
      });
    },
    //涨幅榜
    quotStockIncrease() {
      let that = this;
      quotStockIncrease().then(res => {
        console.log("数据返回成功");
        console.log(res);
        // that.increaseTableData = data.items;
        that.increaseTableData = res.data.data;
        that.quotStockIncreaseTimer = setInterval(() => {
          console.log("正在请求");
          that.quotStockIncrease();
          clearInterval(that.quotStockIncreaseTimer);
          console.log("请求结束", that.quotStockIncreaseTimer);
        }, that.formatSeconds(1));
      });
    },
    //涨停跌停数
    quotStockUpdownCount() {
      let that = this;
      quotStockUpdownCount().then(data => {
        console.log("数据返回成功-涨停跌停数", data, 100);
        data = data.data.data;
        let upList = [];
        let downList = [];
        let xAxis = [];
        //对比涨跌数组长度来取x轴的下标 保证数据显示完整
        if (data.upList.length > data.downList.length) {
          data.upList.forEach((item, index) => {
            upList.push(item.count);
            xAxis.push(item.time);
            if (data.downList[index]) {
              downList.push(data.downList[index].count);
            }
          });
        } else {
          data.downList.forEach((item, index) => {
            downList.push(item.count);
            xAxis.push(item.time);
            if (data.upList[index]) {
              upList.push(data.upList[index].count);
            }
          });
        }
        that.UdChart.setOption({
          xAxis: {
            data: xAxis
          },
          series: [
            {
              name: "涨停",
              data: upList
            },
            {
              name: "跌停",
              data: downList
            }
          ]
        });
        that.quotStockUpdownCountTimer = setInterval(() => {
          console.log("正在请求 - 涨停跌停数");
          that.quotStockUpdownCount();
          clearInterval(that.quotStockUpdownCountTimer);
          console.log("请求结束 - 涨停跌停数", that.quotStockUpdownCountTimer);
        }, that.formatSeconds(1));
      });
    },
    //成交量
    quotStockTradevol() {
      let that = this;
      quotStockTradevol().then(data => {
        // debugger;
        console.log("数据返回成功 - 成交量", data.data);
        data = data.data.data;
        let volList = [];
        let yesVolList = [];
        data.volList.forEach((item, index) => {
          volList.push({
            time: item.time,
            value: item.count
          });
          let value;
          if (data.yesVolList[index] && data.yesVolList[index].count) {
            value = data.yesVolList[index].count
          } else {
            value = 0;
          }
          yesVolList.push({
            time: item.time,
            value: value
          });
        });
        that.moneyChart.setOption({
          series: [
            {
              name: "今日",
              data: volList
            },
            {
              name: "昨日",
              data: yesVolList
            }
          ]
        });
        that.quotStockTradevolTimer = setInterval(() => {
          console.log("正在请求 - 成交量");
          that.quotStockUpdown();
          clearInterval(that.quotStockTradevolTimer);
          console.log("请求结束 - 成交量", that.quotStockTradevolTimer);
        }, that.formatSeconds(1));
      });
    },
    //个股涨跌
    quotStockUpdown() {
      let that = this;
      quotStockUpdown().then(data => {
        console.log("数据返回成功 - 个股涨跌");
        console.log(data);
        // data=data.data;
        let xAxis = [];
        let series = [];
        data.data.data.infos.forEach(item => {
          xAxis.push(item.title);
          series.push({
            value: item.count,
            status: item.status
          });
        });
        that.meStockChart.setOption({
          title: {
            text: "个股涨跌-" + data.data.data.time,
            textStyle: {
              color: "#FFFFFF"
            }
          },
          xAxis: {
            data: xAxis
          },
          series: [
            {
              data: series
            }
          ]
        });
        that.quotStockUpdownTimer = setInterval(() => {
          console.log("正在请求 - 个股涨跌");
          that.quotStockUpdown();
          clearInterval(that.quotStockUpdownTimer);
          console.log("请求结束 - 个股涨跌", that.quotStockUpdownTimer);
        }, that.formatSeconds(1));
      });
    },
    callBack(row) {
      this.$router.push({
        path: "/stockDetail",
        query: {
          id: row.code
        }
      });
    }
  },
  mounted() {
    //涨停跌停图表初始化
    this.UdChartDrawLine();
    //赚钱效应图表初始化
    this.moneyChartDrawLine();
    //个股涨跌图表初始化
    this.meStockChartDrawLine();
    //图标自适应
    this.echartsResize();
    //国内指数
    this.quotIndexAll();
    //板块指数
    this.quotSectorAll();
    //外盘指数
    this.quotExternalIndex();
    //涨幅榜
    this.quotStockIncrease();
    //涨停跌停数
    this.quotStockUpdownCount();
    //个股涨跌
    this.quotStockUpdown();
    //成交量
    this.quotStockTradevol();
  },
  destroyed() {
    clearInterval(this.quotIndexAllTimer);
    clearInterval(this.quotSectorAllTimer);
    clearInterval(this.quotExternalIndexTimer);
    clearInterval(this.quotStockIncreaseTimer);
    clearInterval(this.quotStockUpdownCountTimer);
    clearInterval(this.quotStockUpdownTimer);
    clearInterval(this.quotStockTradevolTimer);
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="scss">
.home {
  .module-group {
    display: flex;

    .module {
      position: relative;
      flex: 1;
      height: 300px;
      padding: 20px;
      margin-right: 20px;
      margin-bottom: 20px;
      background-color: #222733;
      border-radius: 10px;
      overflow: hidden;

      .module-title {
        font-size: 18px;
        color: #ffffff;
        margin-bottom: 20px;
      }

      .module-title-right {
        position: absolute;
        right: 20px;
        top: 20px;
        font-size: 14px;
        cursor: pointer;
        color: #cdcdcd;
      }

      .news-list {
        font-size: 14px;
        color: #cdcdcd;

        li {
          display: flex;
          line-height: 24px;

          .title {
            margin-right: 20px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
          }

          .content {
            flex: 1;
            margin-right: 22px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
          }
        }
      }
    }

    .mr0 {
      margin-right: 0;
    }

    .mb0 {
      margin-bottom: 0;
    }

    .flex2 {
      flex: 2 0 5%;
      // flex: 2;
    }
  }
}

/* 股票刻度 */
.codekd {
  position: relative;
  top: -26px;
  display: flex;
  justify-content: space-between;
  color: #ffffff;
  margin-left: 55px;
  margin-right: 60px;
  padding-top: 6px;
  border-top: 1px solid #979797;
}
</style>
<!--todo-->
<!-- Add "scoped" attribute to limit CSS to this component only -->
<!--<style scoped lang="scss">-->
<!--.home {-->
<!--  .module-group {-->
<!--    display: flex;-->
<!--    .module {-->
<!--      position: relative;-->
<!--      flex: 1;-->
<!--      height: 300px;-->
<!--      padding: 20px;-->
<!--      margin-right: 20px;-->
<!--      margin-bottom: 20px;-->
<!--      //background-color: #222733;-->
<!--      background-color: rgb(81, 90, 110);-->
<!--      border-radius: 10px;-->
<!--      overflow: hidden;-->
<!--      .module-title {-->
<!--        font-size: 18px;-->
<!--        color: #ffffff;-->
<!--        margin-bottom: 20px;-->
<!--      }-->
<!--      .module-title-right {-->
<!--        position: absolute;-->
<!--        right: 20px;-->
<!--        top: 20px;-->
<!--        font-size: 14px;-->
<!--        cursor: pointer;-->
<!--        color: #cdcdcd;-->
<!--      }-->
<!--      .news-list {-->
<!--        font-size: 14px;-->
<!--        color: #cdcdcd;-->
<!--        li {-->
<!--          display: flex;-->
<!--          line-height: 24px;-->
<!--          .title {-->
<!--            margin-right: 20px;-->
<!--            overflow: hidden;-->
<!--            text-overflow: ellipsis;-->
<!--            white-space: nowrap;-->
<!--          }-->
<!--          .content {-->
<!--            flex: 1;-->
<!--            margin-right: 22px;-->
<!--            overflow: hidden;-->
<!--            text-overflow: ellipsis;-->
<!--            white-space: nowrap;-->
<!--          }-->
<!--        }-->
<!--      }-->
<!--    }-->
<!--    .mr0 {-->
<!--      margin-right: 0;-->
<!--    }-->
<!--    .mb0 {-->
<!--      margin-bottom: 0;-->
<!--    }-->
<!--    .flex2 {-->
<!--      flex: 2 0 100px;-->
<!--      // flex: 2;-->
<!--    }-->
<!--  }-->
<!--}-->
<!--/* 股票刻度 */-->
<!--.codekd {-->
<!--  position: relative;-->
<!--  top: -26px;-->
<!--  display: flex;-->
<!--  justify-content: space-between;-->
<!--  color: #ffffff;-->
<!--  margin-left: 55px;-->
<!--  margin-right: 60px;-->
<!--  padding-top: 6px;-->
<!--  border-top: 1px solid #979797;-->
<!--}-->
<!--</style>-->
