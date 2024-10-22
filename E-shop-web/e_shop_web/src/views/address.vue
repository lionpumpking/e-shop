<script>
export default {
  data () {
    return {
      user: JSON.parse(localStorage.getItem('LoginUser')),
      show: '',
      tableData:[],
      drawer:false,
      adress:'',
      id:'',
      drawer1:false,
    }
  },

  watch:{
    adress() {
      console.log("adress发生了变化")
    }
  },

  methods: {

    getTableData(){

      this.$axios.get(this.$httpurl+'/adress/Getadress',{
        params: {
          userid: this.user.id,
        }
      }).then(res=>res.data).then(res=>{
        console.log(res)
        this.tableData = res.data
      })
    },

    addAdress(){
      let data = new FormData
      data.append('address', this.adress)
      data.append("userid", this.user.id)
      this.$axios.post(this.$httpurl+'/adress/addAdress',data).then(res=>res.data).then(res=>{
        if(res.code === 2000){
          this.$message({
            type:'success',
            message: '添加成功!'
          });
          this.drawer1 = false
          this.getTableData()
        }else{
          this.$message.error(res.msg)
        }
      })
    },

    showEdit(row){
      this.drawer = true
      this.adress=row.adress
      this.id = row.id
    },
    showAdd(){
      this.drawer1 = true
    },

    editAdress(){
      console.log(this.adress)
      let data = new FormData
      data.append('id', this.id)
      data.append('address', this.adress)
      this.$axios.post(this.$httpurl+'/adress/editAdress',data).then(res=>res.data).then(res=>{
        if(res.code === 2000){
          this.$message({
            type:'success',
            message: '修改成功!'
          });
          this.drawer = false
          this.getTableData()
        }else{
          this.$message.error(res.msg)
        }
      })
    },

    removeAdress(row){
      this.$confirm('此操作将永久删除该地址?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let data = new FormData
        data.append('id', row.id)
        this.$axios.post(this.$httpurl+'/adress/deleteAdress',data).then(res=>res.data).then(res=>{
              if(res.code === 2000){
                this.$message({
                  type:'success',
                  message: '删除成功!'
                });
                this.getTableData()
              }else{
                this.$message.error(res.msg)
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
    this.show = this.user.roleid
    console.log(this.show)
    this.getTableData()
  },
}
</script>

<template>
  <div>
    <div>
      <el-button v-show="show===0" type="primary" @click="showAdd"> 添加地址</el-button>
    </div>
    <div>
      <el-table
        :data="tableData"
        style="width: 100%"
        :row-class-name="tableRowClassName">
        <el-table-column
          prop="adress"
          label="地址">
        </el-table-column>

        <el-table-column
          label="操作">
          <template #default="{row}">
          <el-button type="primary" @click="showEdit(row)">编辑</el-button>
          <el-button type="danger" @click="removeAdress(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div>
      <el-drawer
        title="编辑地址"
        :visible.sync="drawer"
        direction="rtl"
        :before-close="adress">
        <el-form label-width="80px" :model="adress">
          <el-form-item label="名称">
            <el-input v-model="adress"></el-input>
            </el-form-item>
          <el-form-item >
            <el-button type="primary" @click="editAdress" >保 存</el-button>
          </el-form-item>
        </el-form>
      </el-drawer>
    </div>

    <div>
      <el-drawer
        title="添加地址"
        :visible.sync="drawer1"
        direction="rtl"
        :before-close="adress">
        <el-form label-width="80px" :model="adress">
          <el-form-item label="名称">
            <el-input v-model="adress"></el-input>
          </el-form-item>
          <el-form-item >
            <el-button type="primary" @click="addAdress" >添 加</el-button>
          </el-form-item>
        </el-form>
      </el-drawer>
    </div>
  </div>
</template>

<style scoped lang="scss">

</style>
