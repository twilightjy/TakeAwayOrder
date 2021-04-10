import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Cart from "../views/Cart";
import Info from "../views/Info";
import OrderDetail from "../views/OrderDetail";
import Pay from "../views/Pay";
import Order from "../views/Order";
import Login from "../views/Login";
import Mine from "../views/Mine";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/cart',
    name: 'Cart',
    component: Cart,
  },
  {
    path: '/info',
    component: Info
  },
  {
    path: '/orderDetail',
    component: OrderDetail
  },
  {
    path: '/pay',
    component: Pay
  },
  {
    path: '/order',
    component: Order
  },
  {
    path: '/login',
    component: Login
  },
  {
    path: '/mine',
    component: Mine
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
//在进入没一个页面之前都需要做以下处理
router.beforeEach((to, from, next) => {
  //若进入的是登录页面则移除localStorage里的user信息
  if (to.path.startsWith('/login')) {
    window.localStorage.removeItem('user')
    next()
  } else {
    //其他页面首先进入，取出localStorage里的user信息
    let user = JSON.parse(window.localStorage.getItem('user'))
    //若user信息不存在则跳转至登录页面
    if (!user) {
      next({path: '/login'})
    } else {
      //若user存在则成功跳转至目标页面
      next()
    }
  }
})

export default router
