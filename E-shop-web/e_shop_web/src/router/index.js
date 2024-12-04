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
    name: 'index',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/Index-web.vue'),
    children: [
      {
      path: '/homepage',
      name: 'homepage',
      meta: {
        title: '首页'
      },
      component: () => import('../views/homepage.vue')
    },
    {
      path: '/user',
      name: 'user',
      meta: {
        title: '用户管理'
      },
      component: () => import('../views/Main.vue')
    },
    {
      path: '/userInfo',
      name: 'userInfo',
      meta: {
        title: '个人管理'
      },
      component: () => import('../views/Home.vue')
    },

      {
        path: '/productManage',
        name: 'productManage',
        meta: {
          title: '产品管理'
        },
        component: () => import('../views/productManageByShop.vue')
      },

    {
      path: '/err',
      name: 'error',
      meta: {
        title: '错误'
      },
      component: () => import('../views/error.vue')
    },
      {
        path: '/productType',
        name: 'productType',
        meta: {
          title: '错误'
        },
        component: () => import('../views/productTypeManger.vue')
      },
      {
        path: '/shopType',
        name: 'shopType',
        meta: {
          title: '店铺类型管理'
        },
        component: () => import('../views/shopTypeManger.vue')
      },
      {
        path: '/shop',
        name: 'shop',
        meta: {
          title: '店铺管理'
        },
        component: () => import('../views/shopManger.vue')
      },
      {
        path: '/shoppingCart',
        name: 'shoppingCart',
        meta: {
          title: '购物车'
        },
        component: () => import('../views/shoppingCart.vue')
      },
      {
        path: '/ShopShoppingCart',
        name: 'ShopShoppingCart',
        meta: {
          title: '购物车'
        },
        component: () => import('../views/ShopShoppingCart.vue')
      },
      {
        path: '/address',
        name: 'address',
        meta: {
          title: '购物车'
        },
        component: () => import('../views/address.vue')
      },
      {
        path: '/applyForShop',
        name: 'applyForShop',
        meta: {
          title: '购物车'
        },
        component: () => import('../views/applyForShop.vue')
      },
      {
        path: '/msg',
        name: 'msg',
        meta: {
          title: '消息中心'
        },
        component: () => import('../views/msg.vue')
      },

    ]
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
  mode: 'history',
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
    axios.get('http://172.20.36.194:8082/user/checkLogin', {
    //   axios.get('http://localhost:8082/user/checkLogin', {
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
