/************************* 变量文件 *************************/
const ip = '';// api接口请求域名
const host = 'http://192.168.5.100:8003';// 资源服务器
const xxjobAdminUrl='http://127.0.0.1:8082';// xxjob-admin管理后台地址
// const baseUrl = 'http://mock-api.com/WNnQarnY.mock';
const baseUrl = ip + '/api';// api接口请求域名

/********************* 图片上传管理 ***********************/
const delUploadImageUrl = host + '/uploads/del';// 删除已上传图片地址
const upload = ip + '/upload/';
const uploadIdCardUrl = host + '/upload/zn_IdCard';// 身份证上传
const uploadBankCardUrl = host + '/upload/zn_bankCard';// 银行卡图片上传
// 注意：删除图片，只需要改地址，然后引入组件即可使用
module.exports = {
    ip,
    baseUrl,
    delUploadImageUrl,
    upload,
    uploadIdCardUrl,
    uploadBankCardUrl,
    xxjobAdminUrl
};

