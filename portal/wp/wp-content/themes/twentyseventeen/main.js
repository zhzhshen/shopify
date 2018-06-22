import ProductLib from "./product-lib.min.js"
import "./product-lib.min.css"
Vue.use(ProductLib)

var vue_rest = new Vue({
  el: "#vue_rest",
  data() {
    return {
      sampleInput1 :"",
      sampleInput2 :"",
    }
  },
})
