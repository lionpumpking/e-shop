<script>

export default {
  name: "Login",
  data() {
    return {
      qrcode: '',
      idrole: '',
      flag:false,
      code: '',
      backgroundImg:require('../../public/980.png'),
      captchaUrl: require('@/assets/logo.png'),
      result: '',
      dialogImageUrl:'',
      uploadData: {
        directory:"user",
        name:'',
      },
      resign:{
        uid: '',
        name: '',
        password: '',
        sex: 2,
        age: 0,
        roleid: 1,
        phone: '',
        username: '',
      },
      form: {
        username: '',
        password: '',
        verCode: '',
        valid: true,
      },
      form1: {
        username: '',
        name: '',
        password: '',
        sex: 2,
        age: 0,
        roleid: 1
      },
      formLabelWidth: '120px',
      // 表单验证，需要在 el-form-item 元素中增加 prop 属性
      rules: {
        username: [
          {required: true, message: '账号不可为空', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '密码不可为空', trigger: 'blur'}
        ],
        name: [
          {required: true, message: '账号不可为空', trigger: 'blur'}
        ],
        verCode: [
          {required: true, message: '验证码不可为空', trigger: 'blur'}
        ]
      },
      // 对话框显示和隐藏
      dialogVisible1: false,
    }
  },

  watch:{
    resign(newV,oldV){
      console.log(newV,oldV)
    },
  },

  methods: {
    // 删除图片
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    //传递图片的地址
    handlePictureCardPreview(file) {
      console.log(file)
      this.dialogImageUrl = file
      this.resign.uid = file;
    },

    //登录接口
    onSubmit() {
      // 为表单绑定验证功能
      let data = new FormData
      data.append('verCode', this.form.verCode);
      data.append('username', this.form.username);
      data.append('password', this.form.password);

      if (this.form.username === '' || this.form.password === '') {
        this.$message.info("账号和密码不可为空")
        return
      }

      this.$axios.post(this.$httpurl + '/user/login', data).then(res=>res.data).then(res => {
        console.log("这里是返回的登录信息",res)
        if(res.code === 2000){
          this.$message.success('登录成功');
          localStorage.setItem('LoginUser', JSON.stringify(res.data));
          this.$router.push('/index');
        }
        if(res.code === 4000){
          this.$message.error(res.msg);
        }
      })

    },
    //点击注册按钮后显示注册页面
    enroll() {

      this.dialogVisible1 = true
    },

    //注册接口
    upuser() {
      console.log(this.resign)
      let data = new FormData();
      data.append('username', this.resign.username);
      data.append('password', this.resign.password);
      data.append('name', this.resign.name);
      data.append('sex', this.resign.sex);
      data.append('age', this.resign.age);
      data.append('roleid', this.resign.roleid);
      data.append('phone', this.resign.phone);
      data.append('uid',this.resign.uid)
      this.$axios.post(this.$httpurl + '/user/signin', data).then(res => res.data).then(res => {
          console.log("这里是注册的返回结果",res)
        if(res.code === 2000){
          this.$message.success(res.msg);
          this.dialogVisible1 = false;
            //推进路由，进入首页
          this.$message.success(res.msg)
          }
        else if(res.code === 4000){
          this.$message.error(res.msg);
        }
      })
    },

    // 获取验证码
    verCode() {
      this.$axios.get(this.$httpurl + '/verCode/get', {responseType: 'blob'}).then(res => {
        this.captchaUrl = URL.createObjectURL(res.data)
      })
    },

  },


  mounted() {
    //获取验证码显示在页面中
    this.verCode();
    let reg = new RegExp("(^|&)" + "code" + "=([^&]*)(&|$)", "i");
    let r = window.location.search.substr(1).match(reg); //获取url中"?"符后的字符串并正则匹配
    let context = "";
    if (r != null) context = r[2];
    reg = null;
    r = null;
    this.code=context
    if (context !== "") {
      this.getToken();
    }
  },
}

 //  let id = "用户端"
 //  const ws = new WebSocket(`ws://172.20.36.194:8087/ws/${id}`);
 //  ws.addEventListener("open", (Event) => {
 //    //console.log(evevt);
 //    console.log("WebSocket连接成功")
 //  })
 //
 //  ws.addEventListener("close", (event) => {
 //
 //    console.log("WebSocket连接关闭")
 //  })
 //
 //  ws.addEventListener("message", (event) => {
 //    let res=event.data
 // if( res.startsWith("id")) {
 //   console.log(event.data.split(":")[1])
 //   this.idrole = res
 // }
 //    else //console.log(res)
 //
 //  })
 //
 //  window.onbeforeunload = function () {
 //    ws.send(id + " " + this.form.username + "断开连接")
 //  }



</script>

<template>
  <div id="build">
  <div>
<!--    登录页面-->
    <el-form ref="loginForm" :model="form" :rules="rules" label-width="80px" class="login-box">
      <h3 class="login-title">欢迎登录</h3>
      <el-form-item label="账 号" prop="username">
        <el-input type="text" placeholder="请输入账号" v-model="form.username"/>
      </el-form-item>
      <el-form-item label="密 码" prop="password">
        <el-input @keyup.enter.native="onSubmit('loginForm')" type="password" placeholder="请输入密码" v-model="form.password"/>
      </el-form-item>
      <el-form-item label="验证码" prop="verCode">
      <div style="display: flex; justify-content: center">
        <el-input @keyup.enter.native="onSubmit('loginForm')" v-model="form.verCode" prefix-icon="el-icon-user" style="width:60%;
          margin-right: 10px" placeholder="请输入验证码">
        </el-input>
        <img :src="captchaUrl"  width="110px " height="33px"  alt="加载失败"/>
        <i title="点击刷新" @click="verCode" class="el-icon-refresh-right" style="margin-left: 5px;margin-top: 13px"></i>
      </div>
      </el-form-item>
      <el-form-item style="">
      <el-button-group >
        <el-button type="primary" style="margin-left: 100px;" v-on:click="enroll('loginForm')">注 册</el-button>
<!--        <el-button type="primary" style="margin-left: 15px;" v-on:click="onSubmit1('loginForm')">微信扫码登录</el-button>-->
        <el-button type="primary"  style="margin-left: 30px;" v-on:click="onSubmit('loginForm')">登 录</el-button>
      </el-button-group>
      </el-form-item>
    </el-form>

<!--    注册页面-->
    <el-dialog title="注 册" :visible.sync="dialogVisible1"   width="30%" append-to-body>
      <el-form :model="resign" :rules="rules">
        <el-form-item label="头像" prop="resign.uid" :label-width="formLabelWidth">
          <el-upload
            action="http://172.20.36.194:8082/file/upload"
            :data="uploadData"
            list-type="picture-card"
            :on-success="handlePictureCardPreview"
            :on-remove="handleRemove">
            <i class="el-icon-plus"></i>
          </el-upload>
          <!--          <el-image v-else  :src="dialogImageUrl" style="width:300px"/>-->
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
          </el-dialog>
          <sapn>如果上传多张，只会留下最后一张</sapn>
        </el-form-item>

        <el-form-item label="账 号:" prop="username" :label-width="formLabelWidth">
          <el-input style="width:300px" v-model="resign.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户名:" prop="name" :label-width="formLabelWidth">
          <el-input style="width:300px" v-model="resign.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item  label="密 码:" prop="password" :label-width="formLabelWidth">
          <el-input type="password" style="width:300px" v-model="resign.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性 别:" :label-width="formLabelWidth">
          <el-radio-group v-model="resign.sex">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
            <el-radio :label="2">保密</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="账户类型:" :label-width="formLabelWidth">
          <el-radio-group v-model="resign.roleid">
            <el-radio :label="1">用户</el-radio>
            <el-radio :label="0">商家</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年 龄:" :label-width="formLabelWidth">
          <el-input style="width:300px" v-model="resign.age" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手 机:" :label-width="formLabelWidth">
          <el-input style="width:300px" v-model="resign.phone" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="upuser ">注 册</el-button>
      </div>
    </el-dialog>
  </div>
  </div>
</template>

<style lang="scss" scoped>
#build{
  background:url("../../public/980.png");
  width:100%;
  height:100%;
  position: fixed;
}

.login-box {
  border: 1px solid #DCDFE6;
  width: 350px;
  background: #ffffff;
  margin: 180px auto;
  padding: 35px 35px 15px 35px;
  border-radius: 5px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  box-shadow: 0 0 25px #909399;
}

.login-title {
  text-align: center;
  margin: 0 auto 40px auto;
  color: #303133;
}
</style>
