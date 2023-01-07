<template>
    <div class="user-edit">
        <component-form :data="form" :width="80"></component-form>
    </div>
</template>
<script>
import { edit,resetPassword } from '@/api/user'

export default {
    name:'user-edit',
    data () {
        return {
            form:{
                labelWidth:'200px',
                formFields:{
                    id:'',
                    username: '',
                    phone:'',
                    email:'',
                    nickName:'',
                    realName:'',
                    sex:1,
                    createWhere:1,
                    status:1,
                },
                formLabel:[
                    {prop: 'username', title: '账号', type: 'input',placeholder:'请输入账号'},
                    {prop: 'phone', title: '手机号', type: 'input',placeholder:'请输入手机号码'},
                    {prop: 'email', title: '邮箱', type: 'input',placeholder:'请输入邮箱'},
                    {prop: 'nickName', title: '昵称', type: 'input',placeholder:'请输入昵称'},
                    {prop: 'realName', title: '真实姓名', type: 'input',placeholder:'请输入真实姓名'},
                    {prop: 'sex', title: '性别',type: 'radio',options:[
                            {label:'男',value:1},
                            {label:'女',value:2}],
                        change:val => this.form.formFields.sex = val
                    },
                    {prop: 'createWhere', title: '创建来源',type: 'radio',options:[
                            {label:'web',value:1},
                            {label:'android',value:2},
                            {label:'ios',value:3}],
                        change:val => this.form.formFields.createWhere = val
                    }
                ],
                buttons:{
                    options:[
                        {title:'提交',type:'primary',loading:false,style:{marginRight:'20px'},click:(form,item) => {
                            this.handleSubmit(edit,form,this,data => this.$emit('handleGetTableData'));
                        }},
                        {title:'重置密码',type:'warning',loading:false,click:(form,item) => {
                            item.loading = true;
                            this.alert('确定要重置密码吗？默认密码 123456',()=>{
                                this.request(resetPassword,form.model.id,this,() => item.loading = false,true,fail => {
                                    item.loading = false;
                                    this.error(fail.msg);
                                });
                            });
                        }},
                    ]
                },
                rules: {
                    username: [
                        { required: true, message: '请输入账号', trigger: 'blur' },
                        { min: 3, max: 20, message: '长度在 20 个字符内', trigger: 'blur' }
                    ],
                    phone: [
                        { required: true, message: '请输入手机号码', trigger: 'blur' },
                        { pattern:this.validator.regExpPhone, message: '手机号码不正确', trigger: 'blur' }
                    ],
                    email: [
                        { required: true, message: '请输入邮箱', trigger: 'blur' },
                        { pattern:this.validator.regExpEmail, message: '邮箱不正确', trigger: 'blur' }
                    ],
                    nickName: [
                        { required: true, message: '请输入昵称', trigger: 'blur' },
                        { min: 1, max: 20, message: '长度在 20 个字符内', trigger: 'blur' }
                    ],
                    realName: [
                        { required: true, message: '请输入真实姓名', trigger: 'blur' },
                        { min: 1, max: 20, message: '长度在 20 个字符内', trigger: 'blur' }
                    ],
                }
            }
        }
    },
    methods:{
        currentData(currentData){
            currentData && Object.keys(this.form.formFields).forEach(key => this.form.formFields[key] = currentData[key]);
        },
        change(val){this.form.pid = val;}
    }
}
</script>
<style lang="css" scoped>
.custom-tree-node {
    flex: 1;
    /*display: flex;*/
    /*align-items: center;*/
    /*justify-content: space-between;*/
    font-size: 14px;
    padding-right: 8px;
}
</style>