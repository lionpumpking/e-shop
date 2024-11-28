<script>

export default {
  data () {
    return {
      user: JSON.parse(localStorage.getItem('LoginUser')),
      pageNum: 1,
      pageSize: 20,
      order: '',
      products: [],
      type: [],
      typeIndex: '',
      refresh: false,
      toMessage: true,
      lowPrice: 0,
      highPrice: 999999,
      name: '',
      orderByPrice: 'ase',
      freight: -1,
      noMore: false,
      loading: false,
      show_home: true,
      show_detail: false,
      dialogImageUrl: '',
      dialogVisible: false,
      product: [],
      showdetail: false,
      buy: false,
      num: 0,
      adress: '',
      Toadress: '',
      pay: false,
      message: '',
      msg: false
    }
  },

  computed: {
    combinedData () {
      return this.typeIndex + this.name + this.freight + this.orderByPrice + this.highPrice
        + this.lowPrice
    }
  },

  watch: {
    combinedData (newValue, oldValue) {
      this.refresh = true
      // console.log(this.refresh)
      console.log('这里搜索条件发生了变化：', oldValue, '变为了', newValue)
    },
    toMessage (newValue, oldValue) {
      setTimeout(() => {
        this.toMessage = true
      }, 3000)
    }
  },

  methods: {
// 手动点击查询
    handleSearchByHand () {
      this.pageNum = 1
      this.handleSearch()
    },
    handleChange (num) {
      console.log('这里选择了数量', num)
    },

    showPay (atate) {
      if (atate === 0) {
        this.buy = false
      } else {
        this.buy = false
        this.pay = true
      }
    },

    buyNow () {
      this.showdetail = false
      this.buy = true
    },
    addOrder () {
      if (this.Toadress === '') {
        this.$message.warning('请填写收货地址')
        return
      }
      let data = new FormData
      data.append('productid', this.product.productid)
      data.append('userid', this.user.id)
      data.append('num', this.num)
      data.append('destination', this.Toadress)
      this.$axios.post(this.$httpurl + '/order/GetOrder', data).then(res => res.data).then(res => {
        if (res.code === 2000) {
          this.$message.success('下单成功')
          this.price = res.data
          this.buy = false
          this.pay = false
        } else {
          this.$message.warning(res.msg)
        }
      })

    },

    addCart () {
      let data = new FormData
      console.log(this.product.id)
      data.append('productid', this.product.productid)
      data.append('userid', this.user.id)
      this.$axios.post(this.$httpurl + '/shoppingcart/addShoppingcart', data).then(res => res.data).then(res => {
        if (res.code === 2000) {
          this.$message.success('添加成功')
        } else {
          this.$message.warning(res.msg)
        }
      })
    },
    handleClose () {
      this.showdetail = false
    },

    getAdress () {

      this.$axios.get(this.$httpurl + '/adress/Getadress', {
        params: {
          userid: this.user.id,
        }
      }).then(res => res.data).then(res => {
        console.log(res)
        this.adress = res.data
      })
    },
    //根据条件查找对应商品
    handleSearch () {
      // this.$message.success("加载中...")
      let data = new FormData
      if (this.freight === true) {
        this.freight = 0
      } else {
        this.freight = -1
      }
      if (this.order === '0') {
        this.orderByPrice = 'asc'
      } else {
        this.orderByPrice = 'desc'
      }
      data.append('typeid', this.typeIndex)
      data.append('lowPrice', this.lowPrice)
      data.append('highPrice', this.highPrice)
      data.append('orderByPrice', this.orderByPrice)
      data.append('freight', this.freight)
      data.append('name', this.name)
      data.append('pageNum', this.pageNum)
      data.append('pageSize', 20)
      // console.log(data)
      this.$axios.post(this.$httpurl + '/product/search', data).then(res => res.data).then(res => {
        // console.log(this.refresh)
        if (res.data.records < this.pageSize) {
          this.noMore = true
          this.loading = false
          if (this.toMessage) {
            this.$message.info('没有更多了')
          }
          this.toMessage = false
          this.refresh = false
        }
        if (this.refresh === true) {
          this.products = res.data.records
        } else if (this.refresh === false) {
          this.products.push(...res.data.records)
        }
        this.refresh = false
      })
    },

    detail (scope) {
      console.log('点击了详情页', scope)
      this.product = scope
      this.showdetail = true
      // this.$message.success("点击了详情页面，但是我还没有做好")
      this.show_detail = true
    },

    handleScroll () {
      const bottomOfWindow = window.innerHeight + window.scrollY >= document.body.offsetHeight
      if (bottomOfWindow) {
        this.pageNum += 1
        this.handleSearch()
      }
    },

    showMsg () {
      console.log('联系')
      this.showdetail = false
      this.msg = true
      console.log(this.msg)
    },
    connectShop () {
      console.log('点击了发送消息')
      let data = new FormData
      data.append('fromid', this.user.id)
      data.append('shopid', parseInt(this.product.ownershopid))
      data.append('msg', this.message)
      data.append('type', 1)
      this.$axios.post(this.$httpurl + '/msg/connect', data).then(res => res.data).then(res => {
        console.log(res)
        if(res.code===2000){
          this.$message.success('消息发送成功')
          this.msg = false
        }else{
          this.$message.warning(res.msg)
          this.msg = false
        }
      })

    },
  },

  beforeDestroy () {
    window.removeEventListener('scroll', this.handleScroll)
  },
  mounted () {
    this.getAdress()
    window.addEventListener('scroll', this.handleScroll)
    //初始化商品类型
    this.$axios.post(this.$httpurl + '/producttype/getAllProductTypes').then(res => res.data).then(res => {
      // //console.log(res)
      this.type = res.data
    })
    //初始化商品
    this.$axios.get(this.$httpurl + '/product/getPage', {
      params: {
        pageNum: 1,
        pageSize: 20
      }
    }).then(res => res.data).then(res => {
      //console.log(res)
      if (res.data.records < this.pageSize) {
        this.noMore = true
        this.loading = false
        if (this.toMessage) {
          this.$message.info('没有更多了')
        }
        this.toMessage = false
      }
      this.products.push(...res.data.records)
    })
  }
}
</script>

<template>
  <keep-alive>
    <div>
      <div>
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
                    {{ item.productType }}
                  </el-option>
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
              <el-col>
                <el-button icon="el-icon-search" type="primary" @click="handleSearchByHand">查询</el-button>
              </el-col>

            </el-row>
          </el-header>

          <el-main>
            <el-row :gutter="20">
              <el-col :span="8" v-for="(product, index) in products" :key="index">
                <el-card @click.native="detail(product)" :body-style="{ padding: '0px' }">
                  <img v-if="product.img!== ''" :src="product.img" style="object-fit: fill; height: 300px" class="image"
                       width="300px" height="300px" alt="商品图片">
                  <img v-else src="https://via.placeholder.com/300" class="image" width="300px" height="300px"
                       alt="商品图片">
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

          <el-drawer
            title="商品详情"
            :visible.sync="showdetail"
            direction="rtl"
            :before-close="handleClose">
            <el-form ref="form" :model="product" label-width="80px">
              <el-form-item>
                <el-image :src="product.img"></el-image>
              </el-form-item>
              <el-form-item label="商品名称">
                <el-input v-model="product.name" disabled></el-input>
              </el-form-item>
              <el-form-item label="商品价格">
                <el-input v-model="product.price" disabled></el-input>
              </el-form-item>
              <el-form-item label="商品描述">
                <el-input v-model="product.description" disabled></el-input>
              </el-form-item>
              <el-form-item label="商品库存">
                <el-input v-model="product.quantity" disabled></el-input>
              </el-form-item>
              <el-form-item label="运费">
                <el-input v-model="product.freight" disabled></el-input>
              </el-form-item>
              <el-form-item label="规格">
                <el-input v-model="product.specification" disabled></el-input>
              </el-form-item>
              <el-form-item label="商品类型">
                <el-input v-model="product.producttype" disabled></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="showMsg">联系商家</el-button>
                <el-button type="primary" @click="addCart">加入购物车</el-button>
                <el-button type="primary" @click="buyNow">立即购买</el-button>
              </el-form-item>
            </el-form>
          </el-drawer>


          <el-dialog
            title="联系商家"
            :visible.sync="msg"
            width="30%"
            :before-close="handleClose">
            <el-input v-model="message" placeholder="请输入消息"> </el-input>
            <span slot="footer" class="dialog-footer">
    <el-button @click="msg = false">取 消</el-button>
    <el-button type="primary" @click="connectShop">确 定</el-button>
  </span>
          </el-dialog>


          <el-drawer
            title="商品详情"
            :visible.sync="buy"
            direction="rtl"
            :before-close="handleClose">
            <el-form ref="form" :model="product" label-width="80px">
              <el-form-item>
                <el-image :src="product.img"></el-image>
              </el-form-item>
              <el-form-item label="商品库存">
                <el-input v-model="product.quantity" disabled></el-input>
              </el-form-item>
              <el-form-item>
                <template>
                  <el-input-number v-model="num" @change="handleChange" :min="1" :max="product.quantity"
                                   label="描述文字"></el-input-number>
                </template>
              </el-form-item>
              <el-form-item>
                <el-select v-model="Toadress" clearable placeholder="请选择">
                  <el-option
                    v-for="item in adress"
                    :key="item.id"
                    :label="item.adress"
                    :value="item.adress">{{ item.adress }}
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="showPay(1)">立即付款</el-button>
              </el-form-item>
            </el-form>
          </el-drawer>

          <el-drawer
            title="商品详情"
            :visible.sync="pay"
            direction="rtl"
            :before-close="handleClose">
            <el-form ref="form" :model="product" label-width="80px">
              <el-form-item>
                <el-button type="primary" @click="addOrder">确认支付</el-button>
                <el-button type="primary" @click="showPay(0)">取消支付</el-button>
              </el-form-item>
            </el-form>
          </el-drawer>

        </el-container>
      </div>
      <div>

      </div>
    </div>

  </keep-alive>
</template>

<style scoped lang="scss">

</style>
