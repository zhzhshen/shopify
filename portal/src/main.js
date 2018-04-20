// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ProductLib from 'product-lib'
import 'product-lib/dist/product-lib.min.css'
import PriceLib from 'price-lib'
import 'price-lib/dist/price-lib.min.css'

Vue.config.productionTip = false
Vue.use(ProductLib)
Vue.use(PriceLib)

new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
