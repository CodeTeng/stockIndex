<!-- table组件 -->
<template>
  <div class='hm-table'>
    <el-table
      :data="tableData"
      size="small"
      :height="height"
      :show-header="showHeader"
      @row-click="columnClick"
    >
      <el-table-column
        v-for="(item,index) in tableColumnData"
        :key="index"
        :prop="item.prop"
        :label="item.label"
        :align="align"
        :show-overflow-tooltip="true" >
        <template slot-scope="scope">
          <div
            :style="{ color: getScopeRowItemStates(item.isDifferentStates,scope,item)}"
            :class="getScopeRowItemStates(item.isDifferentStates,scope,item)"
          >{{ getScopeRowItem(scope,item)  }}</div>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
/**
 * tableColumnData 表格配置项
 * prop == prop
 * label == 该列的名字
 * isDifferentStates == 用于区分有不同状态的值
 * extraTextBefore == 该列显示的额外值 前拼接
 * extraTextAfter == 该列显示的额外值 后拼接
 * defaults == 该列默认值
 *  {
        prop: "date",
        label: "金额%",
        isDifferentStates: true,
        extraTextBefore: "+",
        extraTextAfter: "%",
        defaults: "0"
    },
 */
export default {
  components: {},
  props: {
    tableData: {
      type: Array,
      default: []
    },
    tableColumnData: {
      type: Array,
      default: []
    },
    height: {
      type: String,
      default: "210"
    },
    showHeader: {
      type: Boolean,
      default: true
    },
    align: {
      type: String,
      default: "center"
    }
  },
  data() {
    return {};
  },
  methods: {
    //设置表格行的样式
    getScopeRowItem(scope, item) {
      let data = scope.row[item.prop] ? scope.row[item.prop] : item.defaults;
      let extraTextBefore = item.extraTextBefore ? item.extraTextBefore : "";
      let extraTextAfter = item.extraTextAfter ? item.extraTextAfter : "";
      return extraTextBefore + data + extraTextAfter;
    },
    getScopeRowItemStates(states, scope, item) {
      if (states) {
        if (Number(scope.row[item.prop]) > Number(scope.row["preClosePrice"])) {
          return "#FE1919";
        } else if (
          Number(scope.row[item.prop]) < Number(scope.row["preClosePrice"])
        ) {
          return "#249900";
        } else {
          return "";
        }
      }
      // if (states) {
      //   if (Number(scope.row[item.prop]) > Number(scope.row["preClosePrice"])) {
      //     return "#FE1919";
      //   } else if (
      //     Number(scope.row[item.prop]) < Number(scope.row["preClosePrice"])
      //   ) {
      //     return "#249900";
      //   } else {
      //     return "";
      //   }
      // }
    },
    columnClick(row) {
      this.$emit("columnClickCallBack", row);
    }
  },
  created() {},
  mounted() {}
};
</script>
<style lang='scss' scoped>
//@import url(); 引入公共css类
.hm-table {
  width: 100%;

  /* 重置表格样式 */
  .el-table {
    background-color: rgba(225, 225, 225, 0) !important;
  }

  .el-table tr {
    background-color: #222733 !important;
    border: none !important;
    color: #EDEDED !important;
  }

  .el-table th {
    background-color: #222733 !important;
    border: none !important;
  }

  .el-table td {
    background-color: #222733 !important;
    border: none !important;

  }

  .el-table--small td {
    padding: 10px 0 !important;
    color: #CDCDCD !important;
  }

  .el-table {
    border: none !important;
  }

  .el-table::before {
    height: 0px !important;
  }

  .el-table--enable-row-hover .el-table__body tr:hover>td {
    background-color: #434C62 !important;
  }


  /*重置滚动条样式 --- 表格*/
  .el-table ::-webkit-scrollbar {
    width: 10px;
    height: 10px;
    cursor: pointer;
  }

  /*滑动轨道*/
  .el-table ::-webkit-scrollbar-track {
    border-radius: 10px;
    background-color: rgba(205, 205, 205, .3)
  }

  /*滑块*/
  .el-table ::-webkit-scrollbar-thumb {
    border-radius: 10px;
    background-color: #CDCDCD;

  }

  /*滑块效果*/
  .el-table ::-webkit-scrollbar-thumb:hover {
    border-radius: 10px;
    background-color: #CDCDCD;
  }

  /*重置滚动条样式 ---  详情页面*/
  .table-cont::-webkit-scrollbar {
    width: 1px;
    height: 10px;
    cursor: pointer;
  }

  /*滑动轨道*/
  .table-cont::-webkit-scrollbar-track {
    border-radius: 10px;
    background-color: rgba(205, 205, 205, .3)
  }

  /*滑块*/
  .table-cont::-webkit-scrollbar-thumb {
    border-radius: 10px;
    background-color: #CDCDCD;

  }

  /*滑块效果*/
  .table-cont::-webkit-scrollbar-thumb:hover {
    border-radius: 10px;
    background-color: #CDCDCD;
  }

  /*重置滚动条样式 ---  搜索更多*/
  .search-scroll::-webkit-scrollbar {
    width: 10px;
    height: 10px;
    cursor: pointer;
  }

  /*滑动轨道*/
  .search-scroll::-webkit-scrollbar-track {
    border-radius: 10px;
    background-color: rgba(205, 205, 205, .3)
  }

  /*滑块*/
  .search-scroll::-webkit-scrollbar-thumb {
    border-radius: 10px;
    background-color: #CDCDCD;

  }

  /*滑块效果*/
  .search-scroll::-webkit-scrollbar-thumb:hover {
    border-radius: 10px;
    background-color: #CDCDCD;
  }

  /*重置分页器*/
  .el-pagination .btn-next,
  .el-pagination .btn-prev {
    background: none !important;
    color: #CDCDCD !important;
  }

  .el-pagination.is-background .el-pager li {
    background: none !important;
  }

  .el-pagination.is-background .el-pager li:not(.disabled).active {
    background-color: #ffffff !important;
    color: #000000 !important;
  }

  .el-pagination .el-select .el-input .el-input__inner {
    background: none !important;
    border-color: #CDCDCD !important;
    color: #CDCDCD !important;
  }

  .el-pagination__editor.el-input .el-input__inner {
    background: none !important;
    border-color: #CDCDCD !important;
    color: #CDCDCD !important;
  }
}
</style>