<template>
    <div class="user-add">
        <component-form :data="form" :width="80"></component-form>
    </div>
</template>
<script>
import { add } from '@/api/user'

export default {
    name:'user-add',
    data () {
        return {
            form:{
                labelWidth:'200px',
                formFields:{
                    username: '',
                    password:'',
                    phone:'',
                    email:'',
                    nickName:'',
                    realName:'',
                    sex:1,
                    createWhere:1,
                    status:1,
                    createId:''
                },
                formLabel:[
                    {prop: 'username', title: '账号', type: 'input',placeholder:'请输入账号'},
                    {prop: 'password', title: '密码', type: 'input',password:true,placeholder:'请输入密码'},
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
                        {title:'提交',type:'primary',loading:false,click:(form,item) => {
                            this.handleSubmit(add,form,this,data => this.$emit('handleGetTableData'),true);
                        }},
                    ]
                },
                rules: {
                    username: [
                        { required: true, message: '请输入账号', trigger: 'blur' },
                        { min: 3, max: 20, message: '长度在 20 个字符内', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' },
                        { pattern:this.validator.regExpPassword, message: '密码长度不正确', trigger: 'blur' }
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
                        { min: 3, max: 20, message: '长度在 20 个字符内', trigger: 'blur' }
                    ],
                    realName: [
                        { required: true, message: '请输入真实姓名', trigger: 'blur' },
                        { min: 3, max: 20, message: '长度在 20 个字符内', trigger: 'blur' }
                    ],
                }
            }
        }
    },
    methods:{
        change(val){
            this.form.pid = val;
        },
    },
  mounted() {
      this.form.formFields.createId= this.$ls.get("userInfo").id;
      console.log("当前创建新用户人：",this.form.formFields.createId);
  }
}
</script>
<style lang="css" scoped>

</style>