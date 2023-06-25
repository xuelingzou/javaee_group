import Vue from 'vue'
import VueRouter from 'vue-router'




Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: () => import('../views/login.vue'),
    meta:{
      title: '登录'
    }
  },
  {
    path: '/clientIndex',
    name: 'clientIndex',
    component: () => import('../views/clientIndex.vue')

  },
  {
    path: '/clientAdd',
    name: 'clientAdd',
    component: () => import('../components/login/clientAdd.vue')

  },
  {
    path: '/companyIndex',
    name: 'companyIndex',
    component: () => import('../views/companyIndex.vue')

  },{
    path: '/companyAdd',
    name: 'companyAdd',
    component: () => import('../components/login/companyAdd.vue')

  },
  {
    path: '/adminIndex',
    name: 'adminIndex',
    component: () => import('../views/adminIndex.vue')

  },

  {
    path: '/clientNewOrder',
    name: 'clientNewOrder',
    component: () => import('../components/client/clientNewOrder.vue')
  },
  {
    path: '/clientOrderSearch',
    name: 'clientOrderSearch',
    component: () => import('../components/client/clientOrderSearch.vue')
  },
  {
    path: '/comOrderSearch',
    name: 'comOrderSearch',
    component: () => import('../components/company/comOrderSearch.vue')
  },
  {
    path: '/comOrderManage',
    name: 'comOrderManage',
    component: () => import('../components/company/comOrderManage.vue')
  }, {
    path: '/comOrderManage2',
    name: 'comOrderManage2',
    component: () => import('../components/company/comOrderManage_copy.vue')
  },{
    path: '/clientModi',
    name: 'clientModi',
    component: () => import('../components/admin/userInfo/client/clientModi.vue')
  },{
    path: '/coModi',
    name: 'coModi',
    component: () => import('../components/admin/userInfo/company/coModi.vue')
  },{
    path: '/addLogistics',
    name: 'addLogistics',
    component: () => import('../components/logistics/addLogistics.vue')
  },{
    path: '/logisticsInfo',
    name: 'logisticsInfo',
    component: () => import('../components/logistics/logisticsInfo.vue')
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
