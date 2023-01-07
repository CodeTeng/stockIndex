# stockIndex
### 1. 项目介绍
**一个基于股票实时交易产生的数据分析产品，旨在为特定用户和机构提供定制化的股票数据分析和展示服务**

**项目的核心功能以数据分析和展示为主，主要包含了沪深指数、沪深板块、沪深个股和K线的实时行情查询等功能，内容包含了国内实时指数、涨幅榜、个股涨跌、个股秒级行情、实时日K线行情等**

### 2. 技术选型
**前端技术栈**
| 名称     | 技术                      | 场景 |
| -------- | ------------------------- | ---- |
| 基本骨架 | vue-cli+vue+element+axios |      |
| 报表     | echartsjs                 |      |
| 前端支持 | node webpack 等           |      |

**后端技术栈**
| 名称          | 技术                                                         | 场景         |
| ------------- | ------------------------------------------------------------ | ------------ |
| 基础框架      | springboot、mybatis-springboot、springMVC                    | 项目基础骨架 |
| 安全框架      | boot-security+jwt                                            | 认证与授权   |
| 缓存          | redis                                                        | 缓存         |
| excel表格导出 | easyexcel                                                    |              |
| 小组件        | jode-date 、gson 、guava 、httpClient \| restTemplate 、线程池 |              |
| 定时任务      | xxljob                                                       |              |
| 文档框架      | swagger                                                      |              |
| 分库分表      | sharding-JDBC                                                |              |
| 部署          | nginx                                                        |              |

**整体概况**
![image](https://user-images.githubusercontent.com/82208902/211144849-5d174ede-a7a5-4ed5-b263-ccefb34476ca.png)
****
### 3. 核心业务
**业务结构预览**
![image](https://user-images.githubusercontent.com/82208902/211144893-614b2a38-0eba-4b3d-b550-5c1251babe5e.png)
**业务功能简介**
```
1.定时任务调度服务
	XXL-JOB通过RestTemplate+多线程动态拉去股票接口数据，刷入数据库；
2.国内指数服务
3.板块指数服务
4.涨幅榜展示功能
5.涨停跌停数展示功能
6.成交量对比展示功能
7.个股涨停服务展示功能
8.个股详情展示功能
   	包含分时行情、日k线、周K线图等
9.个股描述服务；
10.报表导出服务
```
