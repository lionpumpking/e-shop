import Vue from 'vue'
import VueRouter from 'vue-router'
import axios from 'axios'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'login',
    component: () => import('../views/login.vue')
  },
  {
    path: '/index',
    name: 'Index-web',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/Index-web.vue'),
    // children: [{
    //   path: '/book',
    //   name: 'book',
    //   meta: {
    //     title: '图书管理'
    //   },
    //   component: () => import('../components/todex/bookManger.vue')
    // },
    // {
    //   path: '/userMange',
    //   name: 'userMange',
    //   meta: {
    //     title: '用户管理'
    //   },
    //   component: () => import('../components/Main.vue')
    // },
    // {
    //   path: '/user',
    //   name: 'user',
    //   meta: {
    //     title: '个人管理'
    //   },
    //   component: () => import('../components/Home.vue')
    // },
    //
    // {
    //   path: '/err',
    //   name: 'error',
    //   meta: {
    //     title: '错误'
    //   },
    //   component: () => import('../components/error.vue')
    // }
    // ]
  },

  // {
  //   path: '/Home',
  //   name: 'Home',
  //
  //   component: () => import('../components/Home.vue')
  // },
  // {
  //   path: '/wx',
  //   name: 'wx',
  //   component: () => import('../components/wx.vue')
  // }

]

const router = new VueRouter({
  // mode: 'history',
  routes
})

const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (to) {
  return VueRouterPush.call(this, to).catch(err => err)
}

router.beforeEach(async (to, from, next) => {
  if (to.path === '/') {
    localStorage.removeItem('LoginUser')
    next()
  } else {
    const admin = JSON.parse(localStorage.getItem('LoginUser'))
    // if(admin==null){
    //   next('/err')
    // }else{
    // 172.20.36.194:8087
    // 60.204.200.197:8083
    axios.get('http://172.20.36.194:8082/user/checkLogin', {
      headers: {
        token: admin.token
      }
    }).then((res) => {
      if (!res.data) {
        console.log('请重新登陆')
        next('/err')
      }
    })
    next()
  }
})

axios.interceptors.response.use(config => {
  // console.log(config.headers['valid'])
  const Valid = config.headers.valid
  if (Valid === 'false') {
    alert('请勿连续点击')
  }
  return config
})
export default router
