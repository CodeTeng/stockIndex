/************************* 公共函数文件(会被加到 vue 实例上) *************************/
export default {
    /**
     * 发送请求统一处理
     * @param method   方法
     * @param params   参数（多个参数，用数组传递，如：[参数1，参数2]）
     * @param _this    vue实例
     * @param success  回调函数
     * @param message  信息提示（为 true表示提示请求回来的提示，false表示不提示，字符串表示提示传递的信息）
     * @param fail     失败回调 (可能是自定义提示信息，可能是回调函数)
     * @param error    异常回调
     */
    request:(method,params,_this,success,message=true,fail,error) => {
        return method(params).then(res => {
            _this.jumpUrl(res,_this,data => {
                success && success(data);
                if (data.msg){
                    if (message == true)return _this.success(data.msg);
                    if (typeof(message)=='string')return _this.success(message);
                }
                //return (data.msg||message) && _this.success(typeof(message)=='string'?message:data.msg);
            },fail);
        }).catch(err => {
            console.log('%c ! Send Request Error','background:#000;color:#bada55',err);
            !error ? _this.error('服务异常') : (typeof(error) == 'string' ? _this.error(error) : error(err));
        });
    },
    /**
     * 表单提交统一处理
     * @param method      请求方法
     * @param form        表单对象
     * @param _this       vue实例
     * @param success     成功回调
     * @param isFormClear 是否清空表单 (添加后清空，编辑后不清空)
     * @param fail        失败回调 (可能是自定义提示信息，可能是回调函数)
     * @param error       异常回调
     */
    handleSubmit:(method,form,_this,success,isFormClear=false,fail,error) => {
        // debugger;
        _this.loading = true;
        form.validate(valid => {
            if (!valid){
                return _this.loading = false;
            }
            method(form.model).then(res => {
                !fail && (_this.loading = false);
                // debugger;
                _this.jumpUrl(res,_this,data => {
                    isFormClear && form.resetFields();
                    data.msg && _this.success(data.msg);
                    return success && success(data);
                },fail);
            }).catch(err => {
                console.log('%c ! Send Form error','background:#000;color:#bada55',err);
                !error ? _this.error('服务异常') : (typeof(error) == 'string' ? _this.error(error) : error(err));
            });
        });
    },
    /**
     * 页面跳转
     * @param res 请求返回的数据对象
     * @param _this vue实例
     * @param success 成功回调
     * @param error 失败回调 (可能是自定义提示信息，可能是回调函数)
     */
    jumpUrl:(res,_this,success,error) => {
        res = res.data ? res.data : res;
        if (res.code == 3){// 无权限
            return _this.$router.push('/401');
        }else if (res.code == 1){// 成功
            return success && success (res.data ? res.data : res);
        }else if (res.code == 0){// 失败 (可能是自定义提示信息，可能是回调函数)
            return !error ? _this.error(res.msg) : (typeof(error) == 'string' ? _this.error(error) : error(res));
        } else {
            _this.error(res.msg,() => {
                _this.$ls.clear();
                _this.$store.dispatch('clearLoginOut');
                _this.$router.push('/login');
                setTimeout(() => window.location.reload(),600);
            });
        }
    },
    /**
     * 截取数组中指定的项
     * @param data
     * @param start
     * @param end
     * @returns {*}
     */
    arraySplice:(data,start,end) => {
        return Array.isArray(data) &&　data.slice(start,start+end);
    },
    /**
     * 删除图片
     * @param url 请求地址
     * @param path 待删除图片路径
     * @param success 删除成功回调
     * @param error 删除失败回调
     * @param type 请求类型
     */
    deluploadImage:function(url,path,success,error,type='post') {
        this.axios.request({
            url:url,
            method:type,
            data:{path:path}
        }).then(res => {
            if(res.data.code == 1){
                return success && success();
            }else{
                return error && error();
            }
        });
    },
    /**
     * 格式化时间格式
     * @param val
     * @returns {string}
     */
    formattime: function (val) {
        if(val == null || val == undefined){
            return "";
        }
        var date=new Date(val);
        var year=date.getFullYear();
        var month=date.getMonth()+1;
        month=month>9?month:('0'+month);
        var day=date.getDate();
        day=day>9?day:('0'+day);
        var hh=date.getHours();
        hh=hh>9?hh:('0'+hh);
        var mm=date.getMinutes();
        mm=mm>9?mm:('0'+mm);
        var ss=date.getSeconds();
        ss=ss>9?ss:('0'+ss);
        var time=year+'-'+month+'-'+day+' '+hh+':'+mm+':'+ss;
        return time;
    },
    /**
     * 批量修改对象直
     * @param data
     * @param val
     * @constructor
     */
    objectforIn:(data,val) => {
        for (var key in data){
            data[key] = val;
        }
    }
}