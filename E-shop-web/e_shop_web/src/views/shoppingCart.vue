<script>
export default{
  data(){
    return{
      user:JSON.parse(localStorage.getItem("LoginUser")),
      tableData:[],
    }
  },

  methods:{
    // 获取购物车数据
    getTableData(){
      this.$axios.get(this.$httpurl + '/shoppingcart/cartByUser',{
        params:{
          userid:this.user.id
        }
      }).then(res=>res.data).then(res=>{
        // //console.log(res)
        this.tableData = res.data
      })
    },
    // 删除购物车物品
    removeCart(row){
      // console.log(row)
      this.$confirm('确认从购物车中移除该商品?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let data = new FormData
        data.append('userid', this.user.id)
        data.append('id', row.id)
        this.$axios.post(this.$httpurl + '/shoppingcart/deleteShoppingcart',data).then(res=>res.data).then(res=>{
          if(res.code === 2000){
            this.$message({
              type:'success',
              message: '从购物车中删除成功'
            });
            this.getTableData()
          }else{
            this.$message({
              type: 'error',
              message: res.msg
            });
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });

    },
  },

  mounted () {
    this.getTableData()
  }
}
</script>

<template>
<div>
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
          label="操作"
          width="180">
          <template #default="{row}" >
          <el-button type="warning" @click="removeCart(row)" >删 除</el-button>
          </template>
        </el-table-column>

      </el-table>
    </template>
  </div>


</div>
</template>

<style scoped lang="scss">

</style>
