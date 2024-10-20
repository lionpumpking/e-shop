<script >

export default {
  data () {
    return {
      // isCollapse:false
      userInfo : ''
    }
  },
  computed: {
    menu: {
      get () {
        return this.$store.state.menu
      }
    }
  },
  created () {
    this.userInfo = JSON.parse(localStorage.getItem('LoginUser')) || {}  // 获取本地存储的用户信息
    this.getMenu()
    this.load()
  },
  methods: {
    getMenu () {
      this.$axios.get(this.$httpurl +'/menu/list',{
        params: {
          roleid:this.userInfo.roleid
        }
      }).then(res=>res.data).then(res=>{
        this.$store.commit("setMenu", res.data)
        localStorage.setItem("currMenu", JSON.stringify(res.data))
      })
    },

    load () {
      this.$store.commit('setMenu', JSON.parse(localStorage.getItem('currMenu')))
    }
  },

  props: {
    isCollapse: Boolean
  }
}
</script>

<template style="position: fixed; top: 0; left: 0; right: 0; bottom: 0;">
  <el-menu
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
      style="height: 100vh;
      position: fixed;"
      default-active="/userInfo"
      :collapse="isCollapse"
      :collapse-transition="false"
      router>
    <el-menu-item index="/userInfo" style="width:200px;height:0px">
      <span slot="title">个人信息</span>
    </el-menu-item>
      <el-menu-item index="/userInfo">
        <i class="el-icon-s-home"></i>
        <span slot="title">个人信息</span>
      </el-menu-item>

    <el-menu-item :index="'/'+item.menuclick" v-for="(item,i) in menu" :key="i">
      <i :class="item.menuicon"></i>
      <span slot="title">{{item.menuname}}</span>
    </el-menu-item>

<!--    <el-menu-item index="/userMange">-->
<!--      <i class="el-icon-s-home"></i>-->
<!--      <span slot="title">导航二</span>-->
<!--    </el-menu-item>-->
  </el-menu>
</template>

<style scoped lang="scss">
</style>
