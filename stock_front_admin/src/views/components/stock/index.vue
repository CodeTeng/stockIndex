<template>
  <div id="stockapp">
    <el-container>
      <el-header height="50px">
        <div class="header-name">今日股票指数</div>
        <div class="seach">
          <div class="seach-name">股票搜索：</div>
          <div
            class="seach-input"
            @mousemove="mousemove"
            @mouseleave="historySearch = false"
          >

            <div class="input">
              <input
                type="text"
                placeholder="代码"
                v-model="searchText"
                @keyup="inputChange"
              >
            </div>
            <div class="icon">
              <i class="el-icon-search"></i>
            </div>
            <div
              class="history-search"
              v-show="historySearch"
            >
              <div class="content search-scroll">
                <div
                  class="item"
                  v-for="(item,index) in searchData"
                  :key="index"
                  @click="itemClick(item.code)"
                >
                  <span>{{item.code}}</span>
                  <span>{{item.name}}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script>
import { quotStockSearch } from "@/api/stock/home.js";
export default {
  name: "stockSearch",
  data() {
    return {
      historySearch: false,
      searchData: [],
      searchText: ""
    };
  },
  watch: {
    searchText() {
      console.log(this.$router,'当前路由路径');
      this.quotStockSearch();
    },
    historySearch(state) {
      if (!state) {
        this.searchData = [];
      }
    }
  },
  methods: {
    quotStockSearch() {
      quotStockSearch({
        searchStr: this.searchText
      }).then(data => {
        this.historySearch = true;
        this.searchData = data.data.data;
        console.log(data);
      });
    },
    itemClick(code) {
      this.$router.push({
        path: "/details",
        query: {
          id: code
        }
      });
      this.searchData = [];
      this.historySearch = false;
    },
    mousemove() {
      if (this.searchData.length > 0) {
        this.historySearch = true;
      }
    },
    inputChange() {
      if (!this.searchText.length) {
        this.searchData = [];
        this.historySearch = false;
        console.log("----------------------------");
      }
    }
  }
};
</script>

<style lang="scss" scoped>

#stockpp {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
}
.el-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #222733;
  color: #ffffff;
  .header-name {
    font-size: 28px;
    line-height: 40px;
    font-weight: 600;
    color: #ffffff;
    white-space: nowrap;
  }
  .seach {
    display: flex;
    align-items: center;
    .seach-name {
      font-size: 18px;
      color: #cdcdcd;
      margin-right: 10px;
      white-space: nowrap;
    }
    .seach-input {
      position: relative;
      z-index: 10;
      .input {
        width: 300px;
        height: 40px;
        padding-left: 20px;
        padding-right: 70px;
        background-color: #f2f2f2;
        border-radius: 6px;
        overflow: hidden;
        input {
          width: 100%;
          height: 100%;
          outline: none;
          border: none;
          background-color: #f2f2f2;
          font-size: 14px;
          color: #999999;
        }
      }
      .icon {
        position: absolute;
        right: 20px;
        top: 50%;
        margin-top: -15px;
        i {
          font-size: 30px;
          color: #999999;
        }
      }
      .history-search {
        position: absolute;
        left: 0;
        top: 40px;
        width: 300px;
        height: 300px;
        background-color: #222733;
        border-radius: 6px;
        overflow: hidden;

        .content {
          padding-top: 20px;
          padding-bottom: 20px;
          border-radius: 6px;
          margin-top: 20px;
          width: 100%;
          height: 100%;
          background-color: #ffffff;
          color: #000;
          font-size: 14px;
          overflow-y: auto;
          .item {
            display: flex;
            justify-content: space-between;
            padding-left: 20px;
            padding-right: 20px;
            height: 40px;
            line-height: 40px;
            user-select: none;
            cursor: pointer;
            &:hover {
              background-color: #f2f2f2;
            }
          }
        }
      }
    }
  }
}
.el-main {
  background-color: #161522;
}

a {
  text-decoraction: none;
}

.router-link-active {
  text-decoration: none;
}
</style>
