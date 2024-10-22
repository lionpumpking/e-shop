
<script>
import Main from "@/views/Main.vue";
import Aside from "@/views/Aside.vue";
import Hander from "@/views/Hander.vue";

export default {
  components: {Hander, Aside, Main},
  data() {
    return {
      user: JSON.parse(localStorage.getItem("LoginUser")),
      icon: 'el-icon-back',
      //右侧卡片显示
      show2: false,
      img:'https://5efc9a.oss-cn-hangzhou.aliyuncs.com/eshop-user-img/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE%202024-07-06%20144538.png',
      show1: false,
      show3: true,
      //表格显示数据
      tableData: [],
      newUser: '',
      selectValue:'',
      modInfo: '修改资料',
      //修改密码
      modPassword: '修改密码',
      passwordMod: {
        password: '',
        newPassword: '',
        confirmPassword: ''
      },
      //头部的选项索引
      activeIndex: '-2',
      //填写表单规则
      rules: {
        username: [
          {
            required: true,
            message: '账号不可为空',
            trigger: 'blur'
          }
        ],
        name: [
          {
            required: true,
            message: '姓名不可为空',
            trigger: 'blur'
          }
        ],
        password: [
          {
            required: true,
            message: '原密码不可为空',
            trigger: 'blur'
          }
        ],
        newPassword: [
          {
            required: true,
            message: '新密码不可为空',
            trigger: 'blur'
          }
        ],
        confirmPassword: [
          {
            required: true,
            message: '新密码不可为空',
            trigger: 'blur'
          }
        ],
        phone: [
          {
            required: true,
            message: '电话不可为空',
            trigger: 'blur'
          },
          {
            min: 7,
            max: 11,
            message: '电话号码长度必须在7到11位之间',
            trigger: 'blur'
          }
        ],
      },
      uploadData: {
        directory:"user",
        name:'',
      },
    }
  },

  methods:{

    //删除图片
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    //传递图片的地址
    handlePictureCardPreview(file) {
      console.log(file)
      this.dialogImageUrl = file;
      this.newUser.img=file
    },
    //转换页面
    showModUser (){
      this.newUser = JSON.parse(localStorage.getItem("LoginUser"))
      console.log(this.modInfo)
      if(this.show1 === true) this.show1=false
      if(this.show3 === true) this.show3=false
      this.show2=true;
    },
    showModPassword(){
      this.newUser = JSON.parse(localStorage.getItem("LoginUser"))
      console.log(this.modInfo)
      if(this.show2 === true) this.show2=false
      if(this.show3 === true) this.show3=false
      this.show1=true;
    },
    back(){
      this.show1=false;
      this.show2=false;
      this.show3=true;
    },

    //修改基本资料
    mod(){
        console.log(this.newUser)
          let result = new FormData
          result.append('username', this.newUser.username)
          result.append('name', this.newUser.name)
          result.append('id', this.newUser.id)
          result.append('age', this.newUser.age)
          result.append('sex', this.newUser.sex)
          result.append('phone', this.newUser.phone)
          if(this.newUser.img !== '')
          result.append('uid', this.newUser.img)
         if(this.newUser.username=== ""){
           this.$message.error('账号不可为空')
           return
         }
         if(this.newUser.name=== ""){
           this.$message.error('姓名不可为空')
           return
         }
        this.$axios.post(this.$httpurl+'/user/modUserInfo',result).then(res=>res.data).then(res => {
              console.log( res)
              localStorage.setItem("LoginUser",JSON.stringify(res.data))
              this.user = res.data
              this.$message.success('修改成功')
            })
      },
    //修改密码
    ModPassword(){
      console.log(this.passwordMod)
      if(this.passwordMod.newPassword !== this.passwordMod.confirmPassword){
        this.$message.error('两次输入的新密码不一致')
        return
      }
      let data = new FormData
      data.append('password', this.passwordMod.password)
      data.append('username',this.user.username)
      data.append("newPassword", this.passwordMod.newPassword)
      this.$axios.post(this.$httpurl+'/user/modPassword',data).then(res=>res.data).then(res=>{
        //console.log(res)
        if(res.code === 2000){
          this.$message.success('修改成功')
          this.show1=false
          this.show2=false
          this.show3=true
        }else{
          this.$message.error(res.msg)
        }
      })

    },
    //用户获取订单
    getOrder(value){
      if (value === null) value = -2
      console.log(value)
      let data = new FormData
      data.append('state', value)
      data.append('id',this.user.id)
      this.$axios.post(this.$httpurl+'/order/userGetOrder',data).then(res=>res.data).then(res=>{
        this.tableData = res.data
        //console.log(res)
    })
    },
    //点击表格后的操作按钮
    operation(){
      this.$message({
        message: "Too lazy to do this feature",
        type: 'error',
      })
      //用户操作
        if(this.user.roleid === 1){

        }
      //商家操作
      if(this.user.roleid === 2){

      }
    },
    //商家获取订单
    ShopGetOrder(value){
      let data = new FormData
      data.append('state', value)
      data.append('id',this.user.id)
      this.$axios.post(this.$httpurl+'/order/shopGetOrder',data).then(res=>res.data).then(res=>{
        this.tableData = res.data
        //console.log(res)
      })
    }
    },

  mounted(){
    //根据当前用户身份决定初始化表格的数据
    this.uploadData.name = this.user.id
    this.img=this.user.uid
    if(this.user.roleid===2)
    this.ShopGetOrder(-2)
    if(this.user.roleid===1)
    this.getOrder(-2)
  }
}
</script>

<template>
  <el-container style="height: 100%; border: 1px solid #eee;">
<!--    左侧信息表-->
      <el-main type="height: 100%" style="overflow-y: scroll;" >
        <div>
          <el-row :gutter="20" style="margin-top:10px;">
<!--            个人信息-->
            <el-col :span="5">
              <div class="grid-content bg-purple">
                <el-card class="box-card">
                  <div slot="header" class="clearfix">
                    <span>个人中心</span>
                  </div>
                  <div style="margin-right: 1px">
<!--                    <img :src="require('../assets/logo.png')" alt="加载中"  style="width:100%;height: 100%">-->
                    <el-image :src="img"/>
<!--                  </div>-->
<!--                  <div class="demo-fit">-->
<!--                      <el-avatar shape="square" :size="100" fit="contain" :src="require('../assets/logo.png')"> </el-avatar>-->
<!--                    </div>-->
                  </div>
                  <el-divider></el-divider>
                  <div class="register-info">
                    <span class="relation-item">姓名：{{this.user.name}}</span>
                  </div>
                  <div class="register-info" style="margin-top: 10px">
                    <span class="relation-item" >用户类型：{{this.user.roleid ===  2 ?'超级管理员' :this.user.roleid ===  1 ?'用户' : '商家' }}</span>
                  </div>
                  <el-divider></el-divider>
                  <div class="personal-relation">
                    <div class="relation-item">账号:  <div style="float: right; padding-right:20px;">{{this.user.username}}</div></div>
                  </div>
                  <div class="personal-relation">
                    <div class="relation-item">年龄:  <div style="float: right; padding-right:20px;">{{this.user.age}}</div></div>
                  </div>
                  <div class="personal-relation">
                    <div class="relation-item">电话:  <div style="float: right; padding-right:20px;">{{this.user.phone}}</div></div>
                  </div>
                  <div class="personal-relation">
                    <div class="relation-item">性别:  <div style="float: right; padding-right:20px;">{{this.user.sex=== 1 ? '男' : (this.user.sex==='0' ? '女' : '保密')}}</div></div>
                  </div>
                  <el-button style="margin-left: 5%" @click="showModPassword" size="mini" type="primary">{{ modPassword }}</el-button>
                  <el-button style="margin-left: 5%" @click="showModUser" size="mini" type="primary">{{ modInfo }}</el-button>
                </el-card>
              </div>
            </el-col>
            <el-col :span="19">
              <div class="grid-content bg-purple">
<!--原始展示-->
                <el-card  v-show="show3" class="box-card">
                  <div style="margin-left: 100px">
                  </div>
                  <div slot="header" class="clearfix">
                    <span>个人中心</span>
                  </div>
                <div>
                  <div>
<!--                    用户头部导航菜单-->
                    <el-menu v-if="this.user.roleid===1"
                      :default-active="activeIndex"
                      class="el-menu-demo"
                      mode="horizontal"
                      @select="getOrder"
                      background-color="#545c64"
                      text-color="#fff"
                      active-text-color="#ffd04b">
                      <el-menu-item index="-2">全部订单</el-menu-item>
                      <el-menu-item index="0">已下单</el-menu-item>
                      <el-menu-item index="1">已完成</el-menu-item>
                      <el-menu-item index="-1">退款</el-menu-item>
                      <el-menu-item index="2">退款申请中</el-menu-item>
                      <el-menu-item index="3">退款驳回</el-menu-item>
                      <el-menu-item index="5">待支付</el-menu-item>
                      <el-menu-item index="4">待商家确认</el-menu-item>
                    </el-menu>

<!--                    商家头部导航-->
                    <el-menu v-if="this.user.roleid===0"
                             :default-active="activeIndex"
                             class="el-menu-demo"
                             mode="horizontal"
                             @select="ShopGetOrder"
                             background-color="#545c64"
                             text-color="#fff"
                             active-text-color="#ffd04b">
                      <el-menu-item index="-2">全部订单</el-menu-item>
                      <el-menu-item index="2">退款申请</el-menu-item>
                      <el-menu-item index="1">已完成</el-menu-item>
                      <el-menu-item index="-1">退款</el-menu-item>
                      <el-menu-item index="3">退款驳回</el-menu-item>
                      <el-menu-item index="5">待支付</el-menu-item>
                    </el-menu>
<!--                    订单表-->
                  </div>
                  <el-table
                    :data="tableData"
                    stripe
                    style="width: 100%">
                    <el-table-column
                      prop="pressnumber"
                      label="快递单号"
                      width="180">
                    </el-table-column>
                    <el-table-column
                      prop="destination"
                      label="收货地址"
                      width="180">
                    </el-table-column>
                    <el-table-column
                      prop="productid"
                      label="商品名称">
                    </el-table-column>
                    <el-table-column
                      prop="number"
                      label="商品数量">
                    </el-table-column>
                    <el-table-column label="操作">
                      <el-button @click="operation">操作</el-button>
                    </el-table-column>
                  </el-table>
                </div>
                </el-card>
<!--修改密码-->
                <el-card  v-show="show1" class="box-card">
                  <div style="margin-left: 100px">
                  </div>
                  <div slot="header" class="clearfix">
                    <span>修改密码</span>
                  </div>
                  <div>
                    <el-form label-width="80px" :model="passwordMod" ref="ruleForm" size="small" :rules="rules" label-position="right">
                      <el-form-item label="原密码" prop="password" >
                        <el-input  v-model="passwordMod.password" placeholder="原密码不可为空"></el-input>
                      </el-form-item>
                      <el-form-item label="新密码" prop="newPassword">
                        <el-input   v-model="passwordMod.newPassword" placeholder="新密码不可为空"></el-input>
                      </el-form-item>
                      <el-form-item label="新密码" prop="confirmPassword">
                        <el-input   maxlength="18" placeholder="请再次输入新密码" v-model="passwordMod.confirmPassword"></el-input>
                      </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                      <el-button style="margin-left: 80%" @click="back" size="mini" type="primary">返 回</el-button>
                      <el-button style="margin-left: 5%" @click="ModPassword" size="mini" type="primary">修 改</el-button>
                      <!--                      <el-button @click="back" size="mini" type="warning" >关 闭</el-button>-->
                    </div>
                  </div>
                </el-card>

<!--修改资料-->
                <el-card v-show="show2" class="box-card">
                  <div slot="header" class="clearfix">
                    <span>修改基本资料</span>
                  </div>
                  <div>
                    <el-form label-width="80px" :model="newUser" size="small" :rules="rules" label-position="right">
                      <el-form-item label="姓名" prop="name">
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
                        <span>原头像见左侧，上传多张仅最后一张有效</span>
                      </el-form-item>
                      <el-form-item label="姓名" prop="name">
                        <el-input  v-model="newUser.name" placeholder="姓名不可为空"></el-input>
                      </el-form-item>
                      <el-form-item label="账号" prop="username">
                        <el-input   v-model="newUser.username" placeholder="账号不可为空"></el-input>
                      </el-form-item>
                      <el-form-item label="电话" prop="phone">
                        <el-input   v-model="newUser.phone" placeholder="电话不可为空"></el-input>
                      </el-form-item>
                      <el-form-item label="年龄" >
                        <el-input   maxlength="18" v-model="newUser.age"></el-input>
                      </el-form-item>
                      <el-form-item label="性别">
                        <el-radio-group v-model="newUser.sex">
                          <el-radio-button label="1">男</el-radio-button>
                          <el-radio-button label="0">女</el-radio-button>
                          <el-radio-button label="2">保密</el-radio-button>
                        </el-radio-group>
                      </el-form-item>
                    </el-form>
                    <div style="margin-top: -10px">  <span style="font-size: 10px;margin-left: 3%;">提示：带*号的为必填项</span></div>
                    <div slot="footer" class="dialog-footer">
                      <el-button style="margin-left: 80%" @click="back" size="mini" type="primary">返 回</el-button>
                      <el-button style="margin-left: 5%" @click="mod" size="mini" type="primary">修 改</el-button>
                      <!--                      <el-button @click="back" size="mini" type="warning" >关 闭</el-button>-->
                    </div>
                  </div>
                </el-card>
              </div>
            </el-col>

          </el-row>
        </div>
      </el-main>
    </el-container>

</template>


<style lang="scss" scoped>
//卡片样式

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

.box-card {
  width: 100%;
}
//文本样式区
.name-role {
  font-size: 16px;
  padding: 5px;
  text-align:center;
}
.sender{
  text-align:center;
}
.registe-info{
  text-align: center;
  padding-top:10px;
}
.personal-relation {
  font-size: 16px;
  padding: 0 0px 0px;
  margin-right: 1px;
  width: 100%
}

.relation-item {
  padding: 12px;

}
.dialog-footer{
  padding-top:10px ;
  padding-left: 10%;
}
//布局样式区
.el-row {
  margin-bottom: 20px;
  &:last-child {
    margin-bottom: 0;
  }
}
.el-col {
  border-radius: 4px;
}

.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
</style>
