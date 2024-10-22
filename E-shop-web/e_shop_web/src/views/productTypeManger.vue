<script>
export default {
  data () {
    return {
      tableData: [],
      drawer: false,
      productType: '',
      index: 0,
      title: '这里是表单',
      productTypeid: '',
    }
  },
  methods: {
    getTableData () {
      this.$axios.post(this.$httpurl + '/producttype/getAllProductTypes').then(res => res.data).then(res => {
        //console.log(res)
        this.tableData = res.data
      })
    },

    handleShow (index, row) {
      if (index === 2) {
        this.index = 2
        this.title = '编辑产品类型'
        this.productType = row.productType
        this.productTypeid = row.id
      }
      if (index === 1) {
        this.index = 1
        this.title = '新增产品类型'
      }
      this.drawer = true
    },

    addType () {
      console.log('点击了新增')
      let data = new FormData
      data.append('productType', this.productType)
      this.$axios.post(this.$httpurl + '/producttype/addProductType', data).then(res => res.data).then(res => {
        //console.log(res)
        if (res.code === 2000) {
          this.getTableData()
          this.drawer = false
          this.productType = ''
          this.$message.success('添加成功')
        } else {
          this.$message.warning(res.msg)
        }

      })
    },

    editType (row) {
      console.log('点击了编辑', row)
      let data = new FormData
      data.append('id', this.productTypeid)
      data.append('productType', this.productType)
      this.$axios.post(this.$httpurl + '/producttype/updateProductType', data).then(res => res.data).then(res => {
        //console.log(res)
        if (res.code === 2000) {
          this.getTableData()
          this.drawer = false
          this.productType = ''
          this.productTypeid = ''
          this.$message.success('修改成功')
        } else {
          this.$message.warning(res.msg)
        }
      })
    },

    removeType (row) {
      console.log('点击了删除', row)
      let data = new FormData
      data.append('id', row.id)
      this.$axios.post(this.$httpurl + '/producttype/delete', data).then(res => res.data).then(res => {
        //console.log(res)
        if (res.code === 2000) {
          this.$message.success('删除成功')
          this.getTableData()
        } else {
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
      <el-button type="primary" @click="handleShow(1)">新增产品类型</el-button>
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
          prop="productType"
          label="产品类型"
          width="180"
          style="text-align: center">
        </el-table-column>
        <el-table-column
          label="操作"
          width="180"
          style="text-align: center">
          <template #default="{ row, $index }">
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
        <el-form :label-position="productType" label-width="80px" :model="formLabelAlign">
          <el-form-item label="产品类型">
            <el-input v-model="productType"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button v-if="index===2" type="danger" @click="editType(row)">确认修改</el-button>
            <el-button type="danger" v-else @click="addType">新增产品类型</el-button>
          </el-form-item>
        </el-form>
      </el-drawer>
    </div>
  </div>
</template>

<style scoped lang="scss">

</style>
