<script>
export default {
  data () {
    return {
      tableData: [],
      drawer: false,
      rowDetail: [],
      valid:'',
    }
  },

  watch:{
    valid(newData){
      console.log("这里发生了变化",newData)
    }
  },

  methods: {

    //获取店铺列表，超管页面
    getShopList () {
      this.$axios.post(this.$httpurl + '/shop/shopList').then(res => res.data).then(res => {
        // //console.log(res)
        if (res.code === 2000) {
          this.tableData = res.data
          this.$message.success('查询成功')
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    //显示修改界面
    showDrawer (row) {
      this.drawer = true
      this.rowDetail=row
      console.log('行数据', row)
    },

    //关闭修改界面的操作
    handleClose () {
      this.$confirm('确认关闭修改界面吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.drawer = false
        this.$message.success('关闭修改界面')
      }).catch(() => {
        this.$message.info('已取消')
      })
    },
  //提交审核状态
    onSubmit(){
      console.log("点击了提交")
      console.log(this.rowDetail)
      let data = new FormData
      data.append('shopid',this.rowDetail.id)
      data.append('isvalid',this.valid)
      this.$axios.post(this.$httpurl+'/shop/auditShop',data).then(res=>res.data).then(res=>{
        //console.log(res)
        if(res.code === 2000){
          this.$message.success('提交成功')
          this.drawer=false
          // this.$message.error(res.msg)
          this.getShopList()
        }else{
          this.$message.error(res.msg)
        }

      })
    }

  },

  mounted () {
    this.getShopList()
  }
}

</script>

<template>
  <div>

    <div>
      <el-table
        :data="tableData"
        stripe
        style="width: 100%">
        <el-table-column
          prop="id"
          label="Id"
          width="180">
        </el-table-column>
        <el-table-column
          prop="shopname"
          label="店铺名"
          width="180">
        </el-table-column>
        <el-table-column
          prop="shoptype"
          label="店铺类型">
        </el-table-column>
        <el-table-column
          prop="ownerusername"
          label="拥有者者Id">
        </el-table-column>
        <el-table-column
          prop="shopdescription"
          label="店铺描述">
        </el-table-column>
        <el-table-column
          prop="isvalid"
          label="是否审核">
          <template v-slot="scope">
            <el-tag :type="scope.row.isvalid===1 ? 'success':scope.row.isvalid===0 ?'danger' : 'danger'">
              {{ scope.row.isvalid === 1 ? '已审核' : scope.row.isvalid === 0 ? '未审核' : '审核驳回' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column
          label="操作">
          <template #default="{ row, $index }">
            <el-button @click="showDrawer(row)" type="primary">编 辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div>
      <el-drawer
        title="修改店铺状态"
        :visible.sync="drawer"
        direction="rtl"
        :before-close="handleClose">
        <el-form ref="form" :model="rowDetail" label-width="80px">
          <el-form-item label="店铺名称">
            <el-input disabled="true" v-model="rowDetail.shopname"></el-input>
          </el-form-item>
          <el-form-item label="店铺类型">
            <el-input disabled="true" v-model="rowDetail.shoptype"></el-input>
          </el-form-item>
          <el-form-item label="店铺描述">
            <el-input disabled="true" v-model="rowDetail.shopdescription"></el-input>
          </el-form-item>
          <el-form-item label="店铺拥有者Id">
            <el-input  disabled="true" v-model="rowDetail.ownerusername"></el-input>
          </el-form-item>
          <el-form-item label="是否有效">
            <el-select v-model="valid" placeholder="选择审核状态">
              <el-option label="审核通过" value="1"></el-option>
              <el-option label="审核不通过" value="-1"></el-option>
            </el-select>
            </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">提 交</el-button>
            <el-button @click="drawer=false">取 消</el-button>
          </el-form-item>
        </el-form>
      </el-drawer>
    </div>

  </div>


</template>

<style scoped lang="scss">

</style>
