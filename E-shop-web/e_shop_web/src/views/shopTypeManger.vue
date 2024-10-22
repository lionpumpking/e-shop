<script>
export default {
  data () {
    return {
      tableData: [],
      drawer: false,
      shopType:'',
      index:0,
      title:'这里是表单',
      productTypeid: '',
    }
  },
  methods: {
    getTableData () {
      this.$axios.post(this.$httpurl + '/shoptype/getShopTypeList').then(res => res.data).then(res => {
        //console.log(res)
        this.tableData = res.data
      })
    },

    handleShow(index,row) {
      if(index===2){
        this.index=2
        this.title='编辑店铺类型'
        this.shopType=row.shoptype
        this.shopTypeid=row.id
      }
      if (index===1){
        this.index=1
        this.title='新增店铺类型'
      }
      this.drawer=true
    },

    addType () {
      console.log('点击了新增')
      let data = new FormData
      data.append('shoptype', this.shopType)
      this.$axios.post(this.$httpurl+'/shoptype/addShoptype',data).then(res=>res.data).then(res=>{
        //console.log(res)
        if(res.code===2000){
          this.getTableData()
          this.drawer=false
          this.shopType=''
          this.$message.success("添加成功")
        }else {
          this.$message.warning(res.msg)
        }

      })
    },

    editType (row) {
      console.log('点击了编辑',row)
      let data = new FormData
      data.append('id', this.shopTypeid)
      data.append('shoptype', this.shopType)
      this.$axios.post(this.$httpurl+'/shoptype/modShopType',data).then(res=>res.data).then(res=>{
        //console.log(res)
        if (res.code===2000){
          this.getTableData()
          this.drawer=false
          this.shopType=''
          this.shopTypeid=''
          this.$message.success("修改成功")
        }else{
          this.$message.warning(res.msg)
        }
      })
    },

    removeType (row) {
      console.log('点击了删除',row)
      let data =new FormData
      data.append('shoptype', row.shoptype)
      this.$axios.post(this.$httpurl+'/shoptype/removeShopType',data).then(res=>res.data).then(res=>{
        //console.log(res)
        if(res.code===2000){
          this.$message.success("删除成功")
          this.getTableData()
        }else{
          this.$message.warning(res.msg)
        }
      })
    },
  },

  mounted () {
    this.getTableData()
  },
}
</script>

<template>
  <div>
    <div>
      <el-button type="primary" @click="handleShow(1)">新增店铺类型</el-button>
      <el-table
        :data="tableData"
        border
        style="width: 100%;margin-top: 10px">
        <el-table-column
          prop="id"
          label="id"
          width="180">
        </el-table-column>
        <el-table-column
          prop="shoptype"
          label="店铺类型"
          width="180"
          style="text-align: center">
        </el-table-column>
        <el-table-column
          label="操作"
          width="180"
          style="text-align: center">
          <template  #default="{ row, $index }">
            <el-button type="danger" @click="removeType(row)">删除</el-button>
            <el-button type="primary" @click="handleShow(2,row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div>
      <el-drawer
        :title="title"
        :visible.sync="drawer"
        direction="rtl"
        :before-close="handleClose">
        <el-form :label-position="shopType" label-width="80px" :model="formLabelAlign">
          <el-form-item label="店铺类型">
            <el-input v-model="shopType"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button v-if="index===2" type="danger"  @click="editType(row)">确认修改</el-button>
            <el-button type="danger" v-else @click="addType">新增店铺类型</el-button>
          </el-form-item>
        </el-form>
      </el-drawer>
    </div>
  </div>
</template>

<style scoped lang="scss">

</style>
