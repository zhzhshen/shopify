import Vue from 'vue'
import App from './App'
import ProductLib from './components/index'

Vue.config.productionTip = false

Vue.use(ProductLib)

new Vue({
  el: '#app',
  components: { App },
  template: '<App/>'
})
