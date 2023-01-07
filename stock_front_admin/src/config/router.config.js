/************************ axios请求 ************************/
import { baseUrl } from '@/common/env.js';

const config = {
	// baseUrl:'http://localhost:8080/api'
	baseUrl:baseUrl,
	headers:{},
    withCredentials:false // 跨域配置
};
export default config;