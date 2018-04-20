import Vue from 'vue'
import Router from 'vue-router'
import Product from '@/components/product'
import Price from '@/components/price'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    { path: '/product', component: Product },
    { path: '/price', component: Price }
  ]
})
