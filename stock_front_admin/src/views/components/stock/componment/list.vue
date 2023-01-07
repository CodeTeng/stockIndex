<!-- 列表页 -->
<template>
  <div class='stock-list'>
    <Table
      :tableData="tableData"
      :tableColumnData="tableColumnData"
      :height="'800px'"
      @columnClickCallBack="callBack"
    />
     <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :page-sizes="[10, 20, 30, 40,50]"
      :page-size="pageSize"
      layout="prev, pager, next, sizes, jumper,slot"
      :total="total"
      :background="true"
    >
       &#12288;  <el-button type="info" title="导出" @click="exportExcel">导出数据</el-button>
    </el-pagination>
  </div>
</template>

<script>
import Table from "./table.vue";
import { quotStockAll } from "@/api/stock/list";
import {exportStockExcel} from "@/api/excel/index";

export default {
  components: {
    Table
  },
  props: [],
  data() {
    return {
      tableData: [],
      tableColumnData: [
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
          defaults: "0"
        },
        {
          prop: "preClosePrice",
          label: "前收盘价	",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: "0"
        },
        {
          prop: "tradePrice",
          label: "最新价",
          isDifferentStates: true,
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: "0"
        },
        {
          prop: "upDown",
          label: "涨跌",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: "0"
        },
        {
          prop: "increase",
          label: "涨幅",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: "0"
        },
        {
          prop: "amplitude",
          label: "振幅",
          isDifferentStates: true,
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: "0"
        },
        {
          prop: "tradeAmt",
          label: "成交金额",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: "0"
        },
        {
          prop: "tradeVol",
          label: "成交数量",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: "0"
        },

        {
          prop: "curDate",
          label: "当前日期",
          isDifferentStates: "",
          extraTextBefore: "",
          extraTextAfter: "",
          defaults: "0"
        }
      ],
      //国内指数定时器
      quotStockAllTimer: null,
      pageSize: 20,
      total: 400,
      page: 1
    };
  },
  methods: {
    handleSizeChange(val) {
      this.pageSize=val;
      this.handleCurrentChange(this.page);
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      this.page = val;
      quotStockAll({
        page: this.page,
        pageSize: this.pageSize
      }).then(data => {
        data=data.data.data;
        this.tableData = data.rows;
        this.pageSize = data.size;
        this.total = data.totalPages;
      });
    },
    callBack(row) {
      console.log(row.code);
      this.$router.push({
        path: "/stockDetail",
        query: {
          id: row.code
        }
      });
    },
    //分钟换算
    formatSeconds(time) {
      return time * 60 * 1000;
    },
    quotStockAll() {
      let that = this;
      //分页查询
      quotStockAll({
        page: that.page,
        pageSize: that.pageSize
      }).then(data => {
        console.log("数据返回成功");
        console.log(data);
        data=data.data.data;
        // that.tableData = data.items;
        that.tableData = data.rows;
        that.total = data.totalPages;
        that.quotStockAllTimer = setInterval(() => {
          console.log("正在请求");
          that.quotStockAll();
          clearInterval(that.quotStockAllTimer);
          console.log("请求结束");
        }, that.formatSeconds(1));
      });

      //无分页实现
      // quotStockAll().then(data => {
      //   console.log("数据返回成功");
      //   console.log(data);
      //   // that.tableData = data.items;
      //   that.tableData = data.data;
      //   that.total = data.pages;
      //   that.quotStockAllTimer = setInterval(() => {
      //     console.log("正在请求");
      //     that.quotStockAll();
      //     clearInterval(that.quotStockAllTimer);
      //     console.log("请求结束");
      //   }, that.formatSeconds(1));
      // });
    },
    exportExcel(){
      console.log(this.page,this.pageSize,'export Excel');
       exportStockExcel({page:this.page,pageSize:this.pageSize}).then(res=>{
       console.log(res,"下载......");
       const link = document.createElement('a')
       const blob = new Blob([res.data], { type: 'application/vnd.ms-excel;charset=utf-8' })
       link.style.display = 'none'
       link.href = URL.createObjectURL(blob)
       link.setAttribute('download', `股票涨幅信息表.xlsx`)
       document.body.appendChild(link)
       link.click()
       document.body.removeChild(link)
     });
    }

  },
  created() {},
  mounted() {
    this.quotStockAll({page:this.page,pageSize:this.pageSize});
  },
  destroyed() {
    clearInterval(this.quotStockAllTimer);
  }
};
</script>
<style lang='scss' scoped>
//@import url(); 引入公共css类
.list {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  flex-direction: column;
}
</style>
<!--todo-->
<!--<style lang='scss' scoped>-->
<!--//@import url(); 引入公共css类-->
<!--.stock-list {-->
<!--  width: 100%;-->
<!--  height: 100%;-->
<!--  display: flex;-->
<!--  align-items: center;-->
<!--  background-color: #222733;-->
<!--  flex-direction: column;-->
<!--}-->
<!--</style>-->