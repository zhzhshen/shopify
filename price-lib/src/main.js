import Vue from 'vue'
import App from './App'
import PriceLib from './components/index'

Vue.config.productionTip = false
Vue.use(PriceLib)

new Vue({
  el: '#app',
  components: { App },
  template: '<App/>'
})
