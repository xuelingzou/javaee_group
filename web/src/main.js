import Vue from 'vue'
import App from './App.vue'
import router from './router'

import store from './store'
import axios from 'axios'
import ElementUI from 'element-ui';
import request from "@/network/request";
import 'element-ui/lib/theme-chalk/index.css';

import * as echarts from 'echarts'  //引入echarts

Vue.prototype.$echarts = echarts  //挂载在全局

Vue.use(request)
Vue.use(ElementUI)
//全局配置
Vue.prototype.$axios = axios
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
