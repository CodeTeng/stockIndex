// import http from '../../request/http.js';
import axios from '@/api';

//个股描述
export function quotStockDescribe(params) {
    return axios.request({
        url: '/quot/stock/describe',
        method: 'get',
        params,
    });
}

//个股秒级行情数据查询
export function quotStockScreenSecond(params) {
    return axios.request({
        url: '/quot/stock/screen/second',
        method: 'get',
        params,
    });
}

//个股秒级行情数据查询
export function quotSecondDetail(params) {
    return axios.request({
        url: '/quot/stock/screen/second/detail',
        method: 'get',
        params,
    });
}

//个股分时行情
export function timeSharing(params) {
    return axios.request({
        url: '/quot/stock/screen/time-sharing',
        method: 'get',
        params,
    });
}

//个股日K线数据查询
export function dkline(params) {
    return axios.request({
        url: '/quot/stock/screen/dkline',
        method: 'get',
        params,
    });
}

//个股周K线数据查询
export function weekKline(params) {
    return axios.request({
        url: '/quot/stock/screen/weekkline',
        method: 'get',
        params,
    });
}
