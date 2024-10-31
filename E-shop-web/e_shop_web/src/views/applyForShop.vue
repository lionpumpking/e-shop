<script >
export default {
  data() {
    return {
      user: JSON.parse(localStorage.getItem("LoginUser")),
      dialogImageUrl:'',
      type:'',
      shoptypeid:'',
      form: {
        shopname: '',
        ownerusername: '',
        shopdeescription: '',
        lat: '',
        lon: [],
        shopping:'',
        shoptypeid: '',
      },
      rules: {
        name: [
          {
            required: true,
            message: '不能为空，请正确输入',
            trigger: 'blur'
          },
        ],
      },
      uploadData: {
        directory:"shop",
        name:'',
      },
    }
  },
  methods: {
    apply() {
      console.log('submit!');
      let data = new FormData
      data.append('name', this.form.shopname)
      data.append('ownerid', this.user.id)
      data.append('description', this.form.shopdeescription)
      data.append('lat', this.form.lat)
      data.append('lon', this.form.lon)
      data.append('shopping', this.dialogImageUrl)
      data.append('shoptypeid',this.shoptypeid)
      this.$axios.post(this.$httpurl+'/shop/addShop',data).then(res=>res.data).then(res=>{
        // console.log(res)
        if (res.code === 2000) {
          this.$message({
            type:'success',
            message: '申请成功,等待审核'
          })
          this.$router.push('/index')
        } else {
          this.$message({
            type: 'error',
            message: res.msg,
          })
          this.$router.push('/index')
        }
      })
    },
    //删除图片
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    //传递图片的地址
    handlePictureCardPreview(file) {
      console.log(file)
      this.dialogImageUrl = file;
    },
    getTableData () {
      this.$axios.post(this.$httpurl + '/shoptype/getShopTypeList').then(res => res.data).then(res => {
        console.log(res)
        this.type = res.data
      })
    },
  },
  mounted() {
    this.uploadData.name = this.user.id
    this.getTableData()
  },
  watch:{
    dialogImageUrl(n,o){
      console.log(n,o)
    },
    form(n,o){
      console.log(n,o)
    },
    shoptypeid(n,o){
      console.log(o,n)
    }
  }
}
</script>

<template>
  <el-form ref="form" :model="form" :rules="rules" label-width="80px">
    <el-form-item label="头像" prop="shopping">
      <el-upload
        action="http://172.20.36.194:8082/file/upload"
        :data="uploadData"
        list-type="picture-card"
        :on-success="handlePictureCardPreview"
        :on-remove="handleRemove">
        <i class="el-icon-plus"></i>
      </el-upload>
      <el-dialog >
        <img width="100%" :src="dialogImageUrl" alt="">
      </el-dialog>
      <span>上传多张仅最后一张有效</span>
    </el-form-item>
    <el-form-item label="名称" >
      <el-input  type="textarea" v-model="form.shopname" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="描述" >
      <el-input type="textarea" v-model="form.shopdeescription"></el-input>
    </el-form-item>
    <el-form-item label="经度" >
      <el-input type="textarea" v-model="form.lat"></el-input>
    </el-form-item>
    <el-form-item label="纬度">
      <el-input type="textarea" v-model="form.lon"></el-input>
    </el-form-item>

    <el-form-item label="店铺类型" prop="name">
      <el-select v-model="shoptypeid" placeholder="请选择商品类型">
        <el-option
          v-for="item in type"
          :key="item.id"
          :label="item.shoptype"
          :value="item.id"
        >{{ item.shoptype }}
        </el-option>
      </el-select>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="apply">立即创建</el-button>
      <el-button>取消</el-button>
    </el-form-item>
  </el-form>


</template>

<style scoped lang="scss">
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
  border-radius: 6px;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
  border-radius: 6px;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
  border-radius: 6px;
}
</style>
