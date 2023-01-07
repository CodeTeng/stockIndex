<template>
    <div class="personal-password">
        <component-form :data="form" :width="80"></component-form>
    </div>
</template>
<script>
import md5 from 'md5'
import { editPassword } from '@/api/personal'

export default {
    name:'personal-password',
    data () {
        return {
            formData:null,
            form:{
                labelWidth:'200px',
                formFields:{
                    oldPwd: '',
                    newPwd: '',
                },
                formLabel:[
                    {prop: 'oldPwd', title: '旧密码', type: 'input',password:true,placeholder:'请输入旧密码（ 6 ~ 16 位）'},
                    {prop: 'newPwd', title: '新密码', type: 'input',password:true,placeholder:'请输入新密码（ 6 ~ 16 位）',msg:'密码8到16位（字母，数字，下划线，减号）任意组合',msgStyle:{fontSize:'12px',color:'#606266'}}],
                buttons:{
                    align:'center',
                    options:[
                        {title:'提交',type:'primary',loading:false,click:(form,item) => {
                            item.loading = true;
                            this.request(editPassword,this.form.formFields,this,data => {
                                item.loading = false;
                                this.$emit('handleChildrenCloseDialog');
                                this.success(data.msg + '，即将重新登录',() => {
                                    setTimeout(()=>this.$parent.$parent.$parent.logout(),1000);
                                })
                            },false,fail => {
                                this.error(fail.msg);
                                setTimeout(()=>item.loading = false,2000);
                            });

                            /*form.validate(valid => {
                                if (valid){
                                    item.loading = true;
                                    editPassword(this.form.formFields).then(res => {
                                        if (res.data.code == 1){
                                            this.$emit('handleChildrenCloseDialog');
                                            this.success(res.data.msg + '，即将重新登陆',()=>{
                                                item.loading = false;
                                                setTimeout(()=>this.$parent.$parent.$parent.logout(),1000);
                                            });
                                        }else{
                                            this.error(res.data.msg);
                                            setTimeout(()=>item.loading = false,2000);
                                        }
                                    });
                                }
                            })*/
                        }}
                    ]
                },
                rules: {
                    oldPwd: [
                        { required: true, message: '请输入旧密码', trigger: 'blur' },
                        { pattern:this.validator.regExpPassword, message: '密码格式不正确', trigger: 'blur' }
                    ],
                    newPwd: [
                        { required: true, message: '请输入新密码', trigger: 'blur' },
                        { validator:(rule,val,callback) => {
                            if (this.validator.isEmpty(val)){
                                return callback(new Error('新密码不能为空'));
                            }else if (!this.validator.regExpPassword.test(val)){
                                return callback(new Error('新密码格式不正确'));
                            }else if (this.form.formFields.password == val){
                                return callback(new Error('新密码不能与旧密码一致'));
                            }
                            return callback();
                        }, trigger: 'blur' }
                    ],
                }
            },
        }
    },
    methods:{
        currentData(currentData){
            this.$nextTick(() => {
                currentData.password = '默认密码 123456789';
                currentData&&(this.form.formFields = currentData);
            })
        }
    }
}
</script>
<style lang="css" scoped>

</style>