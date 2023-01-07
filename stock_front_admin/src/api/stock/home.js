// import http from '../../request/http.js';
import axios from '@/api';

//国内指数
export function quotIndexAll(params) {
    return axios.request({
        url: '/quot/index/all',
        method: 'get',
        params,
    });
}

//板块指数
export function quotSectorAll(params) {
    return axios.request({
        url: '/quot/sector/all',
        method: 'get',
        params,
    });
}

//外盘指数
export function quotExternalIndex(params) {
    return axios.request({
        url: '/quot/external/index',
        method: 'get',
        params,
    });
}

//涨幅榜
export function quotStockIncrease(params) {
    return axios.request({
        url: '/quot/stock/increase',
        method: 'get',
        params,
    });
}

//涨停跌停数
export function quotStockUpdownCount(params) {
    return axios.request({
        url: '/quot/stock/updown/count',
        method: 'get',
        params,
    });
}

//个股涨跌
export function quotStockUpdown(params) {
    return axios.request({
        url: '/quot/stock/updown',
        method: 'get',
        params,
    });
}

//成交量对比
export function quotStockTradevol(params) {
    return axios.request({
        url: '/quot/stock/tradevol',
        method: 'get',
        params,
    });
}


//股票搜索
export function quotStockSearch(params) {
    return axios.request({
        url: '/quot/stock/search',
        method: 'get',
        params,
    });
}