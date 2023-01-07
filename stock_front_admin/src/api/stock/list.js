// import http from '../../request/http.js';
import axios from '@/api';
//列表页
export function quotStockAll(params) {
    console.log(params);
    return axios.request({
        url: '/quot/stock/all',
        method: 'get',
        params,
    });
}
