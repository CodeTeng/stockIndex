import axios from '@/api'

/******************* 登陆 *******************/
export const login = (data) => {
	// debugger;
	return axios.request({
		url:'/login',
		method:'post',
		data:data
	});
};

/******************* 退出登陆 *******************/
export const logout = () => {
	return axios.request({
		url:'/logout',
		method:'get',
	});
};

/******************* 获取验证码 *******************/
export const captcha = () => {
    return axios.request({
        url:'/captcha',
        method:'get'
    });
};

// export const test = () => {
// 	return axios.request({
// 		method: 'get',
// 		url: '/test'
// 	})
// }