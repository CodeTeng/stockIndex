(window.webpackJsonp=window.webpackJsonp||[]).push([[19],{"+rKV":function(e,r,t){"use strict";Object.defineProperty(r,"__esModule",{value:!0}),r.updatePermissionsIds=r.getPermissionsIds=r.del=r.resetPassword=r.edit=r.add=r.getTableData=void 0;var a=function(e){return e&&e.__esModule?e:{default:e}}(t("Nlzp"));r.getTableData=function(e){return console.log("P-userPage",e),a.default.request({url:"/users",method:"post",data:e})},r.add=function(e){return a.default.request({url:"/user",method:"post",data:e})},r.edit=function(e){return a.default.request({url:"/user",method:"put",data:e})},r.resetPassword=function(e){return a.default.request({url:"/user/password/"+e,method:"get"})},r.del=function(e){return a.default.request({url:"/user",method:"delete",data:e})},r.getPermissionsIds=function(e){return a.default.request({url:"/user/roles/"+e,method:"get"})},r.updatePermissionsIds=function(e){return a.default.request({url:"/user/roles",method:"put",data:e})}},"5ZPf":function(e,r,t){"use strict";Object.defineProperty(r,"__esModule",{value:!0});var a=t("+rKV");r.default={name:"user-add",data:function(){var e=this;return{form:{labelWidth:"200px",formFields:{username:"",password:"",phone:"",email:"",nickName:"",realName:"",sex:1,createWhere:1,status:1},formLabel:[{prop:"username",title:"账号",type:"input",placeholder:"请输入账号"},{prop:"password",title:"密码",type:"input",password:!0,placeholder:"请输入密码"},{prop:"phone",title:"手机号",type:"input",placeholder:"请输入手机号码"},{prop:"email",title:"邮箱",type:"input",placeholder:"请输入邮箱"},{prop:"nickName",title:"昵称",type:"input",placeholder:"请输入昵称"},{prop:"realName",title:"真实姓名",type:"input",placeholder:"请输入真实姓名"},{prop:"sex",title:"性别",type:"radio",options:[{label:"男",value:1},{label:"女",value:2}],change:function(r){return e.form.formFields.sex=r}},{prop:"createWhere",title:"创建来源",type:"radio",options:[{label:"web",value:1},{label:"android",value:2},{label:"ios",value:3}],change:function(r){return e.form.formFields.createWhere=r}}],buttons:{options:[{title:"提交",type:"primary",loading:!1,click:function(r,t){e.handleSubmit(a.add,r,e,function(r){return e.$emit("handleGetTableData")},!0)}}]},rules:{username:[{required:!0,message:"请输入账号",trigger:"blur"},{min:3,max:20,message:"长度在 20 个字符内",trigger:"blur"}],password:[{required:!0,message:"请输入密码",trigger:"blur"},{pattern:this.validator.regExpPassword,message:"密码长度不正确",trigger:"blur"}],phone:[{required:!0,message:"请输入手机号码",trigger:"blur"},{pattern:this.validator.regExpPhone,message:"手机号码不正确",trigger:"blur"}],email:[{required:!0,message:"请输入邮箱",trigger:"blur"},{pattern:this.validator.regExpEmail,message:"邮箱不正确",trigger:"blur"}],nickName:[{required:!0,message:"请输入昵称",trigger:"blur"},{min:3,max:20,message:"长度在 20 个字符内",trigger:"blur"}],realName:[{required:!0,message:"请输入真实姓名",trigger:"blur"},{min:3,max:20,message:"长度在 20 个字符内",trigger:"blur"}]}}}},methods:{change:function(e){this.form.pid=e}}}},GCx8:function(e,r,t){"use strict";t.r(r);var a=t("KKRE"),u=t("ew5g");for(var s in u)"default"!==s&&function(e){t.d(r,e,function(){return u[e]})}(s);var i=t("KHd+"),n=Object(i.a)(u.default,a.a,a.b,!1,null,"1dd9eca2",null);r.default=n.exports},KKRE:function(e,r,t){"use strict";var a=function(){var e=this.$createElement,r=this._self._c||e;return r("div",{staticClass:"user-add"},[r("component-form",{attrs:{data:this.form,width:80}})],1)},u=[];t.d(r,"a",function(){return a}),t.d(r,"b",function(){return u})},ew5g:function(e,r,t){"use strict";t.r(r);var a=t("5ZPf"),u=t.n(a);for(var s in a)"default"!==s&&function(e){t.d(r,e,function(){return a[e]})}(s);r.default=u.a}}]);