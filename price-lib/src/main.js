import Vue from 'vue'
import App from './App'
import ProductLib from 'product-lib'
import 'product-lib/dist/product-lib.min.css'

Vue.config.productionTip = false
Vue.use(ProductLib)

new Vue({
  el: '#app',
  components: { App },
  template: '<App/>'
})
