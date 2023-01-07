<!-- 详情页面 -->
<template>
  <div class='details'>
    <div class="module-group">
      <div class="module flex2 bigHeight tab-box">
        <div class="tab">
          <div
            :class="{'tab-item':true,'active':tabIndex == index}"
            v-for="(item,index) in tabDate"
            :key="index"
            @click="tabClick(index)"
          >{{item}}</div>
        </div>
        <div class="echarts-content">
          <div
            ref="drawLine"
            class="echarts drawLine"
            :style="{width: '100%', height: '96%', opacity: tabIndex == 0 ? '1':'0',zIndex: tabIndex == 0 ? '10':'0'}"
          ></div>
          <div
            ref="candlestick"
            class="echarts candlestick"
            :style="{width: '100%', height: '96%',opacity: tabIndex == 1 ? '1':'0',zIndex: tabIndex == 1 ? '10':'0'}"
          ></div>
          <!-- 初始化周K线图DIV-->
          <div
              ref="weekDl"
              class="echarts candlestick"
              :style="{width: '100%', height: '96%',opacity: tabIndex == 2 ? '1':'0',zIndex: tabIndex == 2 ? '10':'0'}"
          ></div>
        </div>
      </div>
      <div>
        <div class="module mr0 introduction">
          <div class="title">{{stockDescribe.name+ ' ' + stockDescribe.code}}</div>
          <div class="dl">
            <div class="dt">
              主营业务
            </div>
            <div class="dd">
              <span>{{stockDescribe.business}}</span>
            </div>
          </div>
          <!-- <div class="dl">
            <div class="dt">
              个股概念
            </div>
            <div class="dd">
              <span>在线教育</span>
              <span>在线教育</span>
              <span>在线教育</span>
              <span>在线教育</span>
              <span>在线教育</span>
            </div>
          </div> -->
          <div class="dl">
            <div class="dt">
              所属行业
            </div>
            <div class="dd">
              <span>{{stockDescribe.trade}}</span>
            </div>
          </div>
        </div>
        <div class="module mr0 ul-box">
          <!-- <div class="ul">
            <div class="li">
              <span>最新</span>
              <span>29.03</span>
            </div>
            <div class="li">
              <span>涨跌</span>
              <span>29.03</span>
            </div>
            <div class="li">
              <span>涨幅</span>
              <span>29.03</span>
            </div>
            <div class="li">
              <span>振幅</span>
              <span>29.03</span>
            </div>
            <div class="li">
              <span>总手</span>
              <span>29.03</span>
            </div>
            <div class="li">
              <span>金额</span>
              <span>29.03</span>
            </div>
            <div class="li">
              <span>市盈（静）</span>
              <span>29.03</span>
            </div>
            <div class="li">
              <span>总市值</span>
              <span>29.03</span>
            </div>
            <div class="li">
              <span>涨停</span>
              <span>29.03</span>
            </div>
            <div class="li">
              <span>外盘</span>
              <span>29.03</span>
            </div>
          </div>
          <div class="ul">
            <div class="li">
              <span>开盘</span>
              <span>29.03</span>
            </div>
            <div class="li">
              <span>最高</span>
              <span>29.03</span>
            </div>
            <div class="li">
              <span>最低</span>
              <span>29.03</span>
            </div>
            <div class="li">
              <span>量比</span>
              <span>29.03</span>
            </div>
            <div class="li">
              <span>换手</span>
              <span>29.03</span>
            </div>
            <div class="li">
              <span>换手（实）</span>
              <span class="up">29.03</span>
            </div>
            <div class="li">
              <span>市盈（动）</span>
              <span>29.03</span>
            </div>
            <div class="li">
              <span>流通值</span>
              <span class="down">29.03</span>
            </div>
            <div class="li">
              <span>跌停</span>
              <span>29.03</span>
            </div>
            <div class="li">
              <span>内盘</span>
              <span>29.0303</span>
            </div>
          </div> -->
          <div class="ul">
            <div class="li">
              <span>收盘价</span>
              <span>{{quotSecondDetailData.preClosePrice}}</span>
            </div>
            <div class="li">
              <span>开盘价</span>
              <span>{{quotSecondDetailData.openPrice}}</span>
            </div>
            <div class="li">
              <span>最新价</span>
              <span>{{quotSecondDetailData.tradePrice}}</span>
            </div>
            <div class="li">
              <span>最高价</span>
              <span>{{quotSecondDetailData.highPrice}}</span>
            </div>
          </div>
          <div class="ul">
            <div class="li">
              <span>最低价</span>
              <span>{{quotSecondDetailData.lowPrice}}</span>
            </div>
            <div class="li">
              <span>交易金额</span>
              <span>{{quotSecondDetailData.tradeAmt}}</span>
            </div>
            <div class="li">
              <span>交易数量</span>
              <span>{{quotSecondDetailData.tradeVol}}</span>
            </div>
            <div class="li">
              <span>当前日期</span>
              <span>{{quotSecondDetailData.curDate}}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="module-group">
      <div class="module flex2 mb0">
        <div class="echarts-content">
          <div
            ref="drawBar"
            :style="{width: '100%', height: '100%', opacity: tabIndex == 0 ? '1':'0',zIndex: tabIndex == 0 ? '10':'0'}"
            class="echarts"
          ></div>
          <div
            ref="candlestickBar"
            :style="{width: '100%', height: '100%', opacity: tabIndex == 1 ? '1':'0',zIndex: tabIndex == 1 ? '10':'0'}"
            class="echarts"
          ></div>

          <div
              ref="weekDlBar"
              :style="{width: '100%', height: '100%', opacity: tabIndex == 2 ? '1':'0',zIndex: tabIndex == 2 ? '10':'0'}"
              class="echarts"
          ></div>
        </div>

      </div>
      <div>
        <div class="module mr0 mb0 table">
          <div class="table-title">
            <span>时间</span>
            <span>成交价</span>
            <span>成交量</span>
            <span>成交额</span>
          </div>
          <div class="table-cont">
            <div
              class="item"
              v-for="(item, index) in StockScreenSecond"
              :key="index"
            >
              <span>{{item.date}}</span>
              <span>{{item.tradePrice}}</span>
              <span>{{item.tradeVol}}</span>
              <span>{{item.tradeAmt}}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  quotStockDescribe,
  quotStockScreenSecond,
  quotSecondDetail,
  timeSharing,
  dkline, weekKline
} from "@/api/stock/details";
export default {
  components: {},
  props: [],
  data() {
    return {
      //分时图
      drawLine: null,
      drawBar: null,
      //日k
      candlestick: null,
      candlestickBar: null,
      //周K
      candWeek: null,
      candWeekBar: null,
      //切换表示
      tabIndex: 0,
      tabDate: ["分时", "日K", "周K", "月K"],
      // tabDate: ["分时", "日K"],
      //个股秒级行情数据查询定时器
      quotStockScreenSecondTimer: null,
      //个股分时详情数据
      quotSecondDetailTimer: null,
      //分时图
      timeSharingTimer: null,
      //日K
      dklineTimer: null,
      //个股描述
      stockDescribe: {},
      //股票id
      code: "",
      //个股秒级行情数据查询
      StockScreenSecond: [],
      //个股分时详情数据
      quotSecondDetailData: {},
      //个股分时前收盘价
      maxIntervalData: 0
    };
  },
  methods: {
    //分时图
    drawLineFun() {
      // 基于准备好的dom，初始化echarts实例
      this.drawLine = this.$echarts.init(this.$refs.drawLine);
      let maxInterval = this.maxIntervalData;
      // 绘制图表
      this.drawLine.setOption({
        xAxis: {
          type: "category",
          data: new Array(240),
          axisLine: {
            show: true,
            lineStyle: {
              color: "#FE1919",
              opacity: 0.1
            }
          },
          splitLine: {
            show: true,
            lineStyle: {
              color: ["#FE1919"],
              width: 1,
              type: "dashed",
              opacity: 0.1
            }
          },
          axisTick: {
            show: false
          }
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            animation: false,
            type: "cross",
            lineStyle: {
              color: "#376df4",
              width: 2,
              opacity: 1
            }
          },
          formatter: function(data) {
            let datas = data[0];
            return [
              "" + datas.name + '<hr size=1 style="margin: 3px 0">',
              "最新价: " + datas.value + "<br/>"
            ].join("");
          }
        },
        grid: {
          left: "0",
          right: "80px",
          top: "20px",
          bottom: "1%",
          show: true,
          borderColor: "transparent",
          backgroundColor: "#000000"
        },
        yAxis: {
          show: true,
          type: "value",
          position: "right",
          splitLine: {
            show: true,
            lineStyle: {
              color: ["#FE1919"],
              width: 1,
              opacity: 0.2
            }
          },
          axisLine: {
            show: true,
            lineStyle: {
              color: "#FE1919",
              opacity: 0.2
            }
          },
          axisTick: {
            show: false
          },
          min: function(value) {
            return maxInterval - maxInterval * 0.5;
          },
          max: function(value) {
            return maxInterval + maxInterval * 0.5;
          },
          maxInterval: maxInterval * 0.1,
          axisLabel: {
            formatter: function(value, index) {
              return value.toFixed(2);
            }
          }
        },
        series: [
          {
            type: "line",
            smooth: false,
            symbol: 'none',
            lineStyle: {
              color: "#ffffff"
            },
            itemStyle: {
              borderColor: "#ffffff"
            }
          }
        ]
      });
    },
    //分时图
    drawBarFun() {
      // 基于准备好的dom，初始化echarts实例
      this.drawBar = this.$echarts.init(this.$refs.drawBar);
      // 绘制图表
      this.drawBar.setOption({
        xAxis: {
          type: "category",
          data: new Array(240),
          axisLine: {
            show: true,
            lineStyle: {
              color: "#FE1919",
              opacity: 0.1
            }
          },
          splitLine: {
            show: true,
            lineStyle: {
              color: ["#FE1919"],
              width: 1,
              type: "dashed",
              opacity: 0.1
            }
          },
          axisTick: {
            show: false
          }
        },
        tooltip: {
          formatter: function(data) {
            return [
              "" + data.name + '<hr size=1 style="margin: 3px 0">',
              "成交量: " + data.data.value + "<br/>"
            ].join("");
          }
        },
        grid: {
          left: "0",
          right: "80px",
          top: "4%",
          bottom: "2%",
          show: true,
          borderColor: "transparent",
          backgroundColor: "#000000"
        },
        yAxis: {
          show: true,
          type: "value",
          position: "right",
          splitLine: {
            show: true,
            lineStyle: {
              color: ["#FE1919"],
              width: 1,
              opacity: 0.2
            }
          },
          axisLine: {
            show: true,
            lineStyle: {
              color: "#FE1919",
              opacity: 0.2
            }
          },
          axisTick: {
            show: false
          }
        },
        series: [
          {
            type: "bar",
            lineStyle: {
              color: "#ffffff"
            },
            itemStyle: {
              color: function(value) {
                if (value.data.preClosePrice > value.data.openPrice) {
                  return "#969a55";
                } else {
                  return "#39a9a9";
                }
              }
            }
          }
        ]
      });
    },

    //日K
    candlestickFun() {
      // 基于准备好的dom，初始化echarts实例
      this.candlestick = this.$echarts.init(this.$refs.candlestick);
      this.candlestick.resize();
      // 绘制图表
      this.candlestick.setOption({
        tooltip: {
          trigger: "axis",
          axisPointer: {
            animation: false,
            type: "cross",
            lineStyle: {
              color: "#376df4",
              width: 2,
              opacity: 1
            }
          },
          formatter: function(datas) {
            let data = datas[0];
            let preClosePrice = data.data[8];
            return [
              `${data.name}<br/>`,
              `开盘价: <span style='color:${
                data.data[1] > preClosePrice ? "#FD1050" : "#0CF49B"
              }'>${data.data[1]}</span><br/>`,
              `收盘价: <span style='color:${
                data.data[2] > preClosePrice ? "#FD1050" : "#0CF49B"
              }'>${data.data[2]}</span><br/>`,
              `最低价: <span style='color:${
                data.data[3] > preClosePrice ? "#FD1050" : "#0CF49B"
              }'>${data.data[3]}</span><br/>`,
              `最高价: <span style='color:${
                data.data[4] > preClosePrice ? "#FD1050" : "#0CF49B"
              }'>${data.data[4]}</span><br/>`,
              // `均价: <span>${data.data[5]}</span><br/>`,
              `成交量: <span>${data.data[6]}</span><br/>`,
              `成交金额: <span>${data.data[7]}</span><br/>`
            ].join("");
          }
        },
        xAxis: {
          type: "category",
          axisLine: { show: false, lineStyle: { color: "#FE1919" } },
          axisTick: {
            show: false
          }
        },
        yAxis: {
          scale: true,
          position: "right",
          axisLine: { show: false, lineStyle: { color: "#FE1919" } },
          splitLine: { show: false },
          axisTick: {
            show: false
          }
        },
        grid: {
          left: "0",
          right: "80px",
          top: "20px",
          bottom: "1%",
          show: true,
          borderColor: "transparent",
          backgroundColor: "#000000"
        },
        animation: false,
        series: [
          {
            type: "candlestick",
            name: "日K",
            itemStyle: {
              color: "#FD1050",
              color0: "#0CF49B",
              borderColor: "#FD1050",
              borderColor0: "#0CF49B"
            }
          }
        ]
      });
    },
    //日k
    candlestickBarFun() {
      // 基于准备好的dom，初始化echarts实例
      this.candlestickBar = this.$echarts.init(this.$refs.candlestickBar);
      // 绘制图表
      this.candlestickBar.setOption({
        xAxis: {
          type: "category",
          axisLine: {
            show: false,
            lineStyle: {
              color: "#FE1919",
              opacity: 0.1
            }
          },
          splitLine: {
            show: false,
            lineStyle: {
              color: ["#FE1919"],
              width: 1,
              type: "dashed",
              opacity: 0.1
            }
          },
          axisTick: {
            show: false
          }
        },
        tooltip: {
          formatter: function(data) {
            return [
              "" + data.name + '<hr size=1 style="margin: 3px 0">',
              "成交量: " + data.data.value + "<br/>"
            ].join("");
          }
        },
        grid: {
          left: "0",
          right: "80px",
          top: "4%",
          bottom: "2%",
          show: true,
          borderColor: "transparent",
          backgroundColor: "#000000"
        },
        yAxis: {
          show: true,
          type: "value",
          position: "right",
          splitLine: {
            show: false,
            lineStyle: {
              color: ["#FE1919"],
              width: 1,
              opacity: 0.2
            }
          },
          axisLine: {
            show: false,
            lineStyle: {
              color: "#FE1919",
              opacity: 0.2
            }
          },
          axisTick: {
            show: false
          }
        },
        series: [
          {
            type: "bar",
            lineStyle: {
              color: "#ffffff"
            },
            smooth: true,
            itemStyle: {
              color: function(value) {
                if (value.data.preClosePrice > value.data.openPrice) {
                  return "#969a55";
                } else {
                  return "#39a9a9";
                }
              }
            },
            barMaxWidth: "10px",
            symbol: "none"
          }
        ]
      });
    },

    //周k线初始化
    initWeekDLFun() {
      // 基于准备好的dom，初始化echarts实例
      this.candWeek = this.$echarts.init(this.$refs.weekDl);
      this.candWeek.resize();
      // 绘制图表
      this.candWeek.setOption({
        tooltip: {
          trigger: "axis",
          axisPointer: {
            animation: false,
            type: "cross",
            lineStyle: {
              color: "#376df4",
              width: 2,
              opacity: 1
            }
          },
          formatter: function(datas) {
            let data = datas[0];
            let preClosePrice = data.data[8];
            return [
              `${data.name}<br/>`,
              `开盘价: <span style='color:${
                  data.data[1] > preClosePrice ? "#FD1050" : "#0CF49B"
              }'>${data.data[1]}</span><br/>`,
              `收盘价: <span style='color:${
                  data.data[2] > preClosePrice ? "#FD1050" : "#0CF49B"
              }'>${data.data[2]}</span><br/>`,
              `最低价: <span style='color:${
                  data.data[3] > preClosePrice ? "#FD1050" : "#0CF49B"
              }'>${data.data[3]}</span><br/>`,
              `最高价: <span style='color:${
                  data.data[4] > preClosePrice ? "#FD1050" : "#0CF49B"
              }'>${data.data[4]}</span><br/>`
            ].join("");
          }
        },
        xAxis: {
          type: "category",
          axisLine: { show: false, lineStyle: { color: "#FE1919" } },
          axisTick: {
            show: false
          }
        },
        yAxis: {
          scale: true,
          position: "right",
          axisLine: { show: false, lineStyle: { color: "#FE1919" } },
          splitLine: { show: false },
          axisTick: {
            show: false
          }
        },
        grid: {
          left: "0",
          right: "80px",
          top: "20px",
          bottom: "1%",
          show: true,
          borderColor: "transparent",
          backgroundColor: "#000000"
        },
        animation: false,
        series: [
          {
            type: "candlestick",
            name: "日K",
            itemStyle: {
              color: "#FD1050",
              color0: "#0CF49B",
              borderColor: "#FD1050",
              borderColor0: "#0CF49B"
            }
          }
        ]
      });
    },
    //weekDlBar
    candWeekBarFun() {
      // 基于准备好的dom，初始化echarts实例
      this.candWeekBar = this.$echarts.init(this.$refs.weekDlBar);
      // 绘制图表
      this.candWeekBar.setOption({
        xAxis: {
          type: "category",
          axisLine: {
            show: false,
            lineStyle: {
              color: "#FE1919",
              opacity: 0.1
            }
          },
          splitLine: {
            show: false,
            lineStyle: {
              color: ["#FE1919"],
              width: 1,
              type: "dashed",
              opacity: 0.1
            }
          },
          axisTick: {
            show: false
          }
        },
        tooltip: {
          formatter: function(data) {
            return [
              "" + data.name + '<hr size=1 style="margin: 3px 0">',
              "均价: " + data.data.value + "<br/>"
            ].join("");
          }
        },
        grid: {
          left: "0",
          right: "80px",
          top: "4%",
          bottom: "2%",
          show: true,
          borderColor: "transparent",
          backgroundColor: "#000000"
        },
        yAxis: {
          show: true,
          type: "value",
          position: "right",
          splitLine: {
            show: false,
            lineStyle: {
              color: ["#FE1919"],
              width: 1,
              opacity: 0.2
            }
          },
          axisLine: {
            show: false,
            lineStyle: {
              color: "#FE1919",
              opacity: 0.2
            }
          },
          axisTick: {
            show: false
          }
        },
        series: [
          {
            type: "bar",
            lineStyle: {
              color: "#ffffff"
            },
            smooth: true,
            itemStyle: {
              color: function(value) {
                if (value.data.preClosePrice > value.data.openPrice) {
                  return "#969a55";
                } else {
                  return "#39a9a9";
                }
              }
            },
            barMaxWidth: "10px",
            symbol: "none"
          }
        ]
      });
    },
    //切换
    tabClick(index) {
      // if (!index) {
      //   return;
      // }
      this.tabIndex = index;
      if (index == 1 && this.candlestick == null) {
        this.candlestickFun();
        this.candlestickBarFun();
        this.dkline();
      }
      if (index == 2 && this.candWeek == null) {
        this.initWeekDLFun();
        this.candWeekBarFun();
        this.weekline();
      }
    },
    //echarts自适应宽度
    echartsResize() {
      let that = this;
      window.addEventListener("resize", () => {
        that.drawLine.resize();
        that.drawBar.resize();
        if (that.candlestick != null) {
          that.candlestick.resize();
          that.candlestickBar.resize();
        }
      });
    },
    //分钟换算
    formatSeconds(time) {
      return time * 60 * 1000;
    },
    //个股描述
    quotStockDescribe() {
      quotStockDescribe({
        code: this.code
      }).then(data => {
        console.log("数据返回成功 - 个股描述");
        data=data.data.data;
        this.stockDescribe = { ...data };
        console.log(this.stockDescribe);
      });
    },
    //个股秒级行情数据查询
    quotStockScreenSecond() {
      let that = this;
      quotStockScreenSecond({
        code: that.code
      }).then(data => {
        console.log("数据返回成功 - 个股秒级");
        console.log(data);
        if (data.data.data) {
          // that.StockScreenSecond.push(...data.data.data);
          that.StockScreenSecond=data.data.data;
        }
        that.quotStockScreenSecondTimer = setInterval(() => {
          console.log("正在请求 - 个股秒级");
          that.quotStockScreenSecond();
          clearInterval(that.quotStockScreenSecondTimer);
          console.log("请求结束 - 个股秒级");
        }, that.formatSeconds(1));
      });
    },
    //个股分时详情数据
    quotSecondDetail() {
      let that = this;
      quotSecondDetail({
        code: that.code
      }).then(data => {
        console.log("数据返回成功 - 个股分时");
        console.log(data);
        data=data.data.data;
        that.quotSecondDetailData = { ...data };
        that.quotSecondDetailTimer = setInterval(() => {
          console.log("正在请求 - 个股分时");
          that.quotSecondDetail();
          clearInterval(that.quotSecondDetailTimer);
          console.log("请求结束 - 个股分时");
        }, that.formatSeconds(1));
      });
    },
    //个股分时折线图刷新  收>开 红色
    timeSharing() {
      let that = this;
      timeSharing({
        code: that.code
      }).then(data => {
        console.log("数据返回成功 - 个股分时折线图",data,77777);
        let lineData = [];
        let barData = [];
        let xAxisData = [];
        data=data.data;
        if (data.data[0] && data.data[0].openPrice) {
          this.maxIntervalData = data.data[0].openPrice * 1;
        }else{
          this.maxIntervalData=0;
        }

        this.drawLineFun();
        data.data.forEach(item => {
          lineData.push(item.tradePrice);
          xAxisData.push(item.date);
          barData.push({
            preClosePrice: item.preClosePrice,
            openPrice: item.openPrice,
            value: item.tradeVol
          });
        });
        for (let i = 0; i < 120 - data.data.length; i++) {
          xAxisData.push(null);
        }
        that.drawLine.setOption({
          xAxis: {
            data: xAxisData
          },
          series: [
            {
              data: lineData
            }
          ]
        });
        that.drawBar.setOption({
          xAxis: {
            data: xAxisData
          },
          series: [
            {
              data: barData
            }
          ]
        });
        that.timeSharingTimer = setInterval(() => {
          console.log("正在请求 - 个股分时折线图");
          that.timeSharing();
          clearInterval(that.timeSharingTimer);
          console.log("请求结束 - 个股分时折线图");
        }, that.formatSeconds(1));
      });
    },
    //个股日K线刷新  收>开 红色
    dkline() {
      let that = this;
      dkline({
        code: that.code
      }).then(data => {
        console.log("数据返回成功 - 个股日K线");
        let candlestickData = [];
        let candlestickXAxisData = [];
        let candlestickBarData = [];
        let ma5Data = [];
        data=data.data;
        data.data.forEach(item => {
          candlestickXAxisData.push(item.date);
          candlestickData.push([
            item.openPrice,
            item.closePrice,
            item.lowPrice,
            item.highPrice,
            item.avgPrice,
            item.tradeVol,
            item.tradeAmt,
            item.preClosePrice
          ]);
          candlestickBarData.push({
            preClosePrice: item.closePrice,
            openPrice: item.openPrice,
            value: item.tradeVol
          });
          ma5Data.push({
            value: item.avgPrice
          });
        });
        for (let i = 0; i < 30 - data.data.length; i++) {
          candlestickXAxisData.push(null);
        }
        that.candlestick.setOption({
          xAxis: {
            data: candlestickXAxisData
          },
          series: [
            {
              data: candlestickData
            },
            {
              name: "MA5",
              type: "line",
              data: ma5Data,
              smooth: true,
              showSymbol: false,
              lineStyle: {
                width: 1
              }
            }
          ]
        });
        that.candlestickBar.setOption({
          xAxis: {
            data: candlestickXAxisData
          },
          series: [
            {
              data: candlestickBarData
            }
          ]
        });
        that.dklineTimer = setInterval(() => {
          console.log("正在请求 - 个股日K线");
          that.dkline();
          clearInterval(that.dklineTimer);
          console.log("请求结束 - 个股日K线");
        }, that.formatSeconds(1));
      });
    },
    //个股周K线刷新  收>开 红色
    weekline() {
      let that = this;
      weekKline({
        code: that.code
      }).then(data => {
        console.log("数据返回成功 - 个股周K线",data);
        debugger;
        let candlestickData = [];
        let candlestickXAxisData = [];
        let candlestickBarData = [];
        let ma5Data = [];
       let arr=data.data.data;
        //data=data.data;
        arr.forEach(item => {
          candlestickXAxisData.push(item.mxTime);
          candlestickData.push([
            item.openPrice,
            item.closePrice,
            item.minPrice,
            item.maxPrice,
            item.avgPrice
          ]);
          candlestickBarData.push({
            preClosePrice: item.closePrice,
            openPrice: item.openPrice,
            value: item.avgPrice
          });
          ma5Data.push({
            value: item.avgPrice
          });
        });
        for (let i = 0; i < 30 - arr.length; i++) {
          candlestickXAxisData.push(null);
        }
        that.candWeek.setOption({
          xAxis: {
            data: candlestickXAxisData
          },
          series: [
            {
              data: candlestickData
            },
            {
              name: "MA5",
              type: "line",
              data: ma5Data,
              smooth: true,
              showSymbol: false,
              lineStyle: {
                width: 1
              }
            }
          ]
        });
        that.candWeekBar.setOption({
          xAxis: {
            data: candlestickXAxisData
          },
          series: [
            {
              data: candlestickBarData
            }
          ]
        });
      });
    },
  },
  created() {},
  mounted() {
    //股票id
    this.code = this.$route.query.id;
    //分时图
    this.drawLineFun();
    this.drawBarFun();
    this.echartsResize();
    //个股描述
    this.quotStockDescribe();
    //个股秒级行情数据查询
    this.quotStockScreenSecond();
    //个股分时详情数据
    this.quotSecondDetail();
    //个股分时折线图
    this.timeSharing();
  },
  watch: {
    $route(to, from) {
      clearInterval(this.quotStockScreenSecondTimer);
      clearInterval(this.quotSecondDetailTimer);
      clearInterval(this.timeSharingTimer);
      clearInterval(this.dklineTimer);
      //股票id
      this.code = this.$route.query.id;
      //分时图
      this.drawLineFun();
      this.drawBarFun();
      this.echartsResize();
      //个股描述
      this.quotStockDescribe();
      //个股秒级行情数据查询
      this.quotStockScreenSecond();
      //个股分时详情数据
      this.quotSecondDetail();
      //个股分时折线图
      this.timeSharing();
    }
  },
  destroyed() {
    clearInterval(this.quotStockScreenSecondTimer);
    clearInterval(this.quotSecondDetailTimer);
    clearInterval(this.timeSharingTimer);
    clearInterval(this.dklineTimer);
  }
};
</script>
<style lang='scss' scoped>
//@import url(); 引入公共css类
.details {
  .module-group {
    display: flex;
    .module {
      width: 437px;
      height: 300px;
      padding: 20px;
      margin-right: 20px;
      margin-bottom: 20px;
      background-color: #222733;
      border-radius: 10px;
      .echarts-content {
        position: relative;
        width: 100%;
        height: 100%;
        .echarts {
          position: absolute;
          left: 0;
          right: 0;
          top: 0;
          bottom: 0;
        }
      }
    }
    .tab-box {
      .tab {
        display: flex;
        .tab-item {
          width: 59px;
          height: 30px;
          background-color: #434c62;
          border-radius: 4px;
          margin-right: 20px;
          text-align: center;
          line-height: 30px;
          color: #222733;
          cursor: pointer;
        }
        .active {
          background-color: #ffffff;
          color: #666666;
        }
      }
    }
    .introduction {
      .title {
        text-align: center;
        height: 28px;
        font-size: 20px;
        color: #ffffff;
        margin-bottom: 20px;
      }
      .dl {
        display: flex;
        margin-bottom: 30px;
        .dt {
          white-space: nowrap;
          font-size: 18px;
          line-height: 22px;
          margin-right: 20px;
          color: #ededed;
        }
        .dd {
          font-size: 16px;
          line-height: 22px;
          color: #cdcdcd;
          span {
            margin-right: 20px;
            &:last-child {
              margin-right: 0;
            }
          }
        }
      }
    }
    .ul-box {
      display: flex;
      justify-content: space-between;
      .ul {
        display: flex;
        flex-direction: column;
        // justify-content: space-between;
        .li {
          margin-bottom: 10px;
          span {
            font-size: 14px;
            color: #cdcdcd;
            &:nth-child(1) {
              display: inline-block;
              width: 60px;
              margin-right: 43px;
            }
          }
        }
      }
    }
    .table {
      span {
        font-size: 14px;
        color: #cdcdcd;
        width: 100px;
        line-height: 20px;
        &:nth-child(2) {
          width: 50px;
          text-align: center;
        }
        &:nth-child(3) {
          margin-left: 50px;
          width: 50px;
          text-align: center;
        }
        &:nth-child(4) {
          margin-left: 50px;
        }
      }
      .table-title {
        display: flex;
        margin-right: 1px;
        margin-bottom: 1px;
      }
      .table-cont {
        width: 100%;
        height: 240px;
        overflow-y: auto;
        .item {
          display: flex;
          margin-bottom: 1px;
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
      flex: 1;
    }
    .bigHeight {
      height: 620px;
    }
    .up {
      color: #fe1919 !important;
    }
    .down {
      color: #249900 !important;
    }
  }
}
</style>