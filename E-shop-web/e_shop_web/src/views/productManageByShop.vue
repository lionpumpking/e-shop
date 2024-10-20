<script>

export default {
  data () {
    return {
      user: JSON.parse(localStorage.getItem('LoginUser')),
      activeIndex: '1',
      show: 0,
      tableData: [],
      clicked:false,
      rowDetail: '',
      addProduc:{
        id: '',
        name: '',
        description: '',
        price: '',
        quantity: '',
        freight:'',
        typeid: '',
        specification: '',
        img:''
      },
      add: 1,
      drawer: false,
      productType: [],
      producttype: '',
      dialogImageUrl: '',
      dialogVisible: false,
      uploadData: {
        directory:"product",
        name:'',
      },
      rules: {
        name: [
          {
            required: true,
            message: '不能为空，请正确输入',
            trigger: 'blur'
          },
        ],
      }
    }
  },

  watch: {
    producttype (newV, oldV) {
      console.log('这里类型从id=' + oldV + '变为了id=' + newV)
    },
    clicked(newV){
      console.log(newV)
      if(newV)
      setTimeout(()=>{
        this.clicked=false
      },3000)
    },
    dialogImageUrl(newV, oldV) {
      console.log(newV,oldV)
    }
  },
  methods: {

    //删除图片
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    //传递图片的地址
    handlePictureCardPreview(file) {
      console.log(file)
      this.dialogImageUrl = file;
      this.addProduc.img=file
    },

    handleSelect (value) {
      console.log(value)
      if (value === '1') {
        this.getProduct()
      }
      if (value === '2') {
        this.tableData = []
      }
    },

    getProduct () {
      this.show = 0
      this.add = 1
      this.$axios.get(this.$httpurl + '/product/getPageShop', {
        params: {
          id: this.user.id
        }
      }).then(res => res.data).then(res => {
        // console.log(res)
        if (res.data.length > 0) {
          this.tableData = res.data
          this.$message({
            type: 'success',
            message: '查询成功'
          })
        } else {
          this.$message({
            type: 'warning',
            message: res.msg
          })
        }
      })
    },

    getProductType () {
      this.$axios.post(this.$httpurl + '/producttype/getAllProductTypes').then(res => res.data).then(res => {
        this.productType = res.data
        // console.log(this.productType)
      })
    },

    handleMOd (row, index) {
      console.log(row, index)
      this.rowDetail = row
      this.producttype = row.typeid
      // console.log(this.rowDetail)
      this.save = false
      this.drawer = true
    },

    modProduct () {
      let data = new FormData
      data.append('productid', this.rowDetail.productid)
      data.append('name', this.rowDetail.name)
      data.append('price', this.rowDetail.price)
      data.append('description', this.rowDetail.description)
      data.append('quantity', this.rowDetail.quantity)
      data.append('freight', this.rowDetail.freight)
      data.append('specification', this.rowDetail.specification)
      console.log('leixing', this.prodycttype)
      data.append('typeid', this.producttype)
      console.log(data)
      this.$axios.post(this.$httpurl + '/product/modProduct', data).then(res => res.data).then(res => {
        console.log(res)
        if (res.code === 2000) {
          this.getProduct()
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
          this.drawer = false
        } else {
          this.$message({
            type: 'warning',
            message: '修改失败!'
          })
        }
      })
    },

    delProduct (row, index) {
      // console.log(row, index)
      let data = new FormData
      data.append('id', row.productid)
      this.$confirm('是否确定删除该商品', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.post(this.$httpurl + '/product/removeProduct', data).then(res => res.data).then(res => {
          // console.log(res)
          if (res.code === 2000) {
            this.getProduct()
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
          } else {
            this.$message({
              type: 'warning',
              message: '删除失败!'
            })
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },

    handleClose (done) {
      if (!this.save) {
        this.$confirm('还未保存，是否确认关闭？')
          .then(_ => {
            done()
          })
          .catch(_ => {
          })
      }
    },

    getProductLess () {
      this.show = 0
      this.add = 1
      this.$axios.get(this.$httpurl + '/product/getProductLess', {
        params: {
          id: this.user.id
        }
      }).then(res => res.data).then(res => {
        if (res.data.length > 0) {
          this.tableData = res.data
          this.$message({
            type: 'success',
            message: '查询成功!'
          })
        } else {
          this.$message({
            type: 'warning',
            message: '查询无数据!'
          })
        }
      })
    },

    handleAdd(){
      this.show = 1
      this.add = 0
    },

    addProduct () {
      if(this.clicked){
        this.$message({
          type: 'warning',
          message: '请不要连续点击'
        })
        return
      }
      this.clicked = true
      let data = new FormData
      data.append('id', this.user.id)
      data.append('name', this.addProduc.name)
      data.append('description', this.addProduc.description)
      data.append('price', this.addProduc.price)
      data.append('quantity', this.addProduc.quantity)
      data.append('freight', this.addProduc.freight)
      data.append('typeid', this.addProduc.typeid)
      data.append('specification', this.addProduc.specification)
      data.append('img', this.addProduc.img)
      this.$axios.post(this.$httpurl+'/product/addProduct',data).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code === 2000){
          this.$message.success("新增成功")
        }else{
          this.$message.error(res.msg)
        }
      })
    }

  },

  mounted () {
    this.uploadData.name =this.user.id
    this.getProduct()
    this.getProductType()
  }
}

</script>

<template>
  <div>
    <div>
      <el-menu
        :default-active="activeIndex"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b">
        <el-menu-item style="width: 200px;text-align: center" @click="getProduct" index="1">已上架</el-menu-item>
        <el-menu-item style="width: 200px;text-align: center" @click="getProductLess" index="2">待补货</el-menu-item>
        <el-menu-item style="width: 200px;text-align: center" @click="handleAdd" index="3">新增</el-menu-item>
      </el-menu>
    </div>
    <div v-show="show===0">
      <el-table
        :data="tableData"
        stripe
        style="width: 100%">
        <el-table-column
          prop="productid"
          label="id"
          width="180">
        </el-table-column>
        <el-table-column
          prop="img"
          label="商品图片">
          <template #default="{ row }">
            <el-image :src="row.img"></el-image>
          </template>
        </el-table-column>
        <el-table-column
          prop="name"
          label="商品名"
          width="180">
        </el-table-column>
        <el-table-column
          prop="description"
          label="商品描述">
        </el-table-column>
        <el-table-column
          prop="price"
          label="商品价格">
        </el-table-column>
        <el-table-column
          prop="quantity"
          label="商品数量">
        </el-table-column>
        <el-table-column
          prop="freight"
          label="运费">
        </el-table-column>
        <el-table-column
          prop="specification"
          label="商品规格">
        </el-table-column>
        <el-table-column
          prop="producttype"
          label="商品类型">
        </el-table-column>
        <el-table-column width:1000 label="操作">
          <template #default="{ row, $index }">
            <el-button style="margin-left: 0" @click="handleMOd(row,$index)">修改</el-button>
            <el-button style="margin-left: 0" @click="delProduct(row,$index)">删除</el-button>
          </template>
        </el-table-column>


      </el-table>
    </div>

    <div>
      <el-drawer
        title="修改商品信息"
        :visible.sync="drawer"
        direction="rtl"
        :before-close="handleClose">
        <el-form :model="rowDetail" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="商品图片" prop="img">
            <el-image :src="rowDetail.img" style="width:300px;height: 300px;border: 2px solid #050404"></el-image>
          </el-form-item>
          <el-form-item label="商品名" prop="name">
            <el-input v-model="rowDetail.name"></el-input>
          </el-form-item>
          <el-form-item label="商品描述" prop="name">
            <el-input v-model="rowDetail.description"></el-input>
          </el-form-item>
          <el-form-item label="商品价格" prop="name">
            <el-input v-model="rowDetail.price"></el-input>
          </el-form-item>
          <el-form-item label="商品数量" prop="name">
            <el-input v-model="rowDetail.quantity"></el-input>
          </el-form-item>
          <el-form-item label="商品规格" prop="name">
            <el-input v-model="rowDetail.specification"></el-input>
          </el-form-item>
          <el-form-item label="运费" prop="name">
            <el-input v-model="rowDetail.freight"></el-input>
          </el-form-item>
          <el-form-item label="商品类型" prop="name">
            <el-select v-model="producttype" placeholder="请选择商品类型">
              <el-option
                v-for="item in productType"
                :key="item.id"
                :label="item.productType"
                :value="item.id"
              >{{ item.productType }}
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="modProduct">修改</el-button>
          </el-form-item>
        </el-form>
      </el-drawer>
    </div>


<!--    新增商品-->
    <div v-show="add===0">
      <el-form :model="addProduc" :rules="rules" ref="ruleForm" label-width="100px" style="margin-top: 50px" class="demo-ruleForm">
        <el-form-item label="商品图片" prop="img">
<!--          <el-image :src="addProduc.img" style="width:300px;height: 300px;border: 2px solid #050404"></el-image>-->
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
        <el-form-item label="商品名" prop="name">
          <el-input v-model="addProduc.name"></el-input>
        </el-form-item>
        <el-form-item label="商品描述" prop="name">
          <el-input v-model="addProduc.description"></el-input>
        </el-form-item>
        <el-form-item label="商品价格" prop="name">
          <el-input v-model="addProduc.price"></el-input>
        </el-form-item>
        <el-form-item label="商品数量" prop="name">
          <el-input v-model="addProduc.quantity"></el-input>
        </el-form-item>
        <el-form-item label="商品规格" prop="name">
          <el-input v-model="addProduc.specification"></el-input>
        </el-form-item>
        <el-form-item label="运费" prop="name">
          <el-input v-model="addProduc.freight"></el-input>
        </el-form-item>
        <el-form-item label="商品类型" prop="name">
          <el-select v-model="addProduc.typeid" placeholder="请选择商品类型">
            <el-option
              v-for="item in productType"
              :key="item.id"
              :label="item.productType"
              :value="item.id"
            >{{ item.productType }}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addProduct">添 加</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<style scoped lang="scss">

</style>
