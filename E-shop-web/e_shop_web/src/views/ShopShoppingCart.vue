<script>
export default {
  data () {
    return {
      user: JSON.parse(localStorage.getItem("LoginUser")),
      tableData: [],
    }
  },

  methods:{
    // 获取购物车数据
    getTableData(){
      let data = new FormData
      data.append('userid', this.user.id)
      this.$axios.post(this.$httpurl + '/shoppingcart/merchantGet',data).then(res=>res.data).then(res=>{
        // //console.log(res)
        this.tableData = res.data
      })
    },

  },

  mounted () {
    this.getTableData()
  }
}
</script>

<template>
  <div>
    <template>
      <el-table
        :data="tableData"
        stripe
        style="width: 100%">
        <el-table-column
          prop="img"
          label="图片">
          <template slot-scope="scope">
            <img :src="scope.row.img" alt="图片" style="width: 60px; height: 60px;"/>
          </template>
        </el-table-column>
        <el-table-column
          prop="productName"
          label="名称"
          width="180">
        </el-table-column>
        <el-table-column
          prop="description"
          label="商品描述"
          width="180">
        </el-table-column>
        <el-table-column
          prop="price"
          label="价格"
          width="180">
        </el-table-column>
        <el-table-column
          prop="userid"
          label="加入购物车的用户id"
          width="180">
        </el-table-column>


      </el-table>
    </template>
  </div>

</template>

<style scoped lang="scss">

</style>
