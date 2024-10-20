<script >
import { watch } from 'vue'

export default {
  data(){
    return{
      user: JSON.parse(localStorage.getItem("LoginUser")),
      pageNum:1,
      pageSize:20,
      order:'',
      products: [],
      type:[],
      typeIndex:'',
      refresh:false,
      toMessage:true,
      lowPrice:0,
      highPrice:999999,
      name:'',
      orderByPrice:'ase',
      freight:-1,
      noMore:false,
      loading:false,
      show_home:true,
      show_detail:false,
      dialogImageUrl: '',
      dialogVisible: false,
    }
  },

  computed: {
    combinedData() {
      return this.typeIndex+this.name+this.freight+this.orderByPrice+this.highPrice
      +this.lowPrice;
    }
  },

  watch:{
    combinedData(newValue,oldValue) {
          this.refresh=true
          // console.log(this.refresh)
          console.log("这里搜索条件发生了变化：",oldValue,"变为了",newValue)
        },
    toMessage(newValue,oldValue) {
      setTimeout(()=>{
        this.toMessage=true;
      },3000)
    }
  },

methods: {
// 手动点击查询
  handleSearchByHand(){
    this.pageNum=1;
    this.handleSearch()
  },
    //根据条件查找对应商品
  handleSearch(){
    // this.$message.success("加载中...")
    let data = new FormData
    if(this.freight===true) this.freight=0;
    else this.freight=-1
    if(this.order==='0') this.orderByPrice='asc'
      else this.orderByPrice='desc'
    data.append('typeid', this.typeIndex)
    data.append('lowPrice', this.lowPrice)
    data.append('highPrice', this.highPrice)
    data.append('orderByPrice', this.orderByPrice)
    data.append('freight', this.freight)
    data.append('name', this.name)
    data.append("pageNum",this.pageNum)
    data.append("pageSize", 20)
    // console.log(data)
    this.$axios.post(this.$httpurl + '/product/search', data).then(res => res.data).then(res => {
      // console.log(this.refresh)
      if(res.data.records <this.pageSize) {
        this.noMore = true;
        this.loading=false
        if(this.toMessage)
        this.$message.info('没有更多了')
        this.toMessage = false
      }
      if(this.refresh===true)
       this.products=res.data.records
      else if(this.refresh===false) {
        this.products.push(...res.data.records)
      }
      this.refresh=false;
    })
  },

  detail(){
    console.log("点击了详情页")
    this.$message.success("点击了详情页面，但是我还没有做好")
    this.show_detail=true;
  },

  handleScroll() {
    const bottomOfWindow = window.innerHeight + window.scrollY >= document.body.offsetHeight;
    if (bottomOfWindow) {
      this.pageNum += 1;
      this.handleSearch();
    }
  }
},

beforeDestroy() {
  window.removeEventListener('scroll', this.handleScroll);
},
  mounted() {
    window.addEventListener('scroll', this.handleScroll);
    //初始化商品类型
    this.$axios.post(this.$httpurl + '/producttype/getAllProductTypes').then(res => res.data).then(res => {
      // console.log(res)
      this.type = res.data
    })
    //初始化商品
    this.$axios.get(this.$httpurl + '/product/getPage', {
      params: {
        pageNum: 1,
        pageSize: 20
      }
    }).then(res => res.data).then(res => {
      console.log(res)
      if(res.data.records <this.pageSize) {
        this.noMore = true;
        this.loading=false
        if(this.toMessage)
        this.$message.info('没有更多了')
        this.toMessage=false
      }
      this.products.push(...res.data.records)
    })
  }
}
</script>

<template>
  <keep-alive>

  <el-container v-show="show_home">

<!--    商品搜索栏-->
    <el-header>
      <el-row type="flex" justify="space-between" align="middle">
        <el-col :span="8">
          <el-input placeholder="请输入商品名称" v-model="name">
          </el-input>
        </el-col>
        <el-col :span="10">
          <el-select v-model="typeIndex" placeholder="请选择商品分类">
            <el-option v-for="item in type"
                       :key="item.id"
                       :label="item.productType"
                       :value="item.id">
            {{item.productType}}</el-option>
          </el-select>
        </el-col>
        <el-col :span="8">
          <el-input placeholder="最低价格" v-model="lowPrice">
          </el-input>
        </el-col>
        <el-col :span="8">
          <el-input placeholder="最高价格" v-model="highPrice">
          </el-input>
        </el-col>
        <el-col :span="8">
          <el-select v-model="order" placeholder="请选择排序方式">
            <el-option value="0" label="从低到高">从低到高</el-option>
            <el-option value="1" label="从高到低">从高到低</el-option>
          </el-select>
        </el-col>
        <el-col style="margin-left: 20px" :span="10">
          <el-checkbox v-model="freight">是否免运费</el-checkbox>
        </el-col>
        <el-col >
          <el-button  icon="el-icon-search" type="primary" @click="handleSearchByHand">查询</el-button>
        </el-col>

      </el-row>
    </el-header>

    <el-main>
      <el-row :gutter="20">
        <el-col :span="8" v-for="(product, index) in products" :key="index">
          <el-card @click.native="detail" :body-style="{ padding: '0px' }">
            <img v-if="product.img!== ''" :src="product.img" style="object-fit: none; height: 300px" class="image" width="300px" height="300px" alt="商品图片">
            <img v-else src="https://via.placeholder.com/300" class="image" width="300px" height="300px" alt="商品图片">
<!--            https://via.placeholder.com/300-->
            <div style="padding: 14px;">
              <span 商品名称>商品名称：{{ product.name }}</span>
              <div class="bottom clearfix">
                <time class="time">价格：{{ product.price }}元</time>
              </div>
              <div class="bottom clearfix">
                <time class="time">详情：{{ product.description }}</time>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <div v-if="loading" style="text-align: center" class="loading-more">加载更多...</div>
      <div v-if="noMore" style="text-align: center" class="no-more">没有更多内容了</div>
    </el-main>
  </el-container>
  </keep-alive>
</template>

<style scoped lang="scss">

</style>
