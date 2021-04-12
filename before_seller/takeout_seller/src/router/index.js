import Vue from 'vue'
import VueRouter from 'vue-router'
import ProductManage from '../views/ProductManage'
import Home from "../views/Home";
import AddProduct from '../views/AddProduct'
import OrderManage from '../views/OrderManage'
import EditProduct from '../views/EditProduct'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name:"商品管理",
    component:Home,
    show:true,
    redirect:"/addProduct",
    children:[
      {
        path:'/addProduct',
        name:'添加商品',
        component: AddProduct,
        show:true
      },
      {
        path:'/productManage',
        name:'商品管理',
        component: ProductManage,
        show:true
      },
      {
        path:'/editProduct',
        name:'编辑商品',
        component:EditProduct,
        show:false
      }
    ]
  },
  {
    path: '/',
    name:"订单管理",
    component:Home,
    show:true,
    children:[
      {
        path:'/orderManage',
        name:'订单管理',
        component: OrderManage,
        show:true
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router