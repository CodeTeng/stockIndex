// import http from '../../request/http.js';
import axios from '@/api';

//个股描述
export function exportStockExcel(params) {
    return axios.request({
        url: '/quot/stock/export',
        method: 'get',
        params,
        responseType: 'blob'//表名返回数据库类型
    });
}