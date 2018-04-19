import CreateProduct from './CreateProduct'
import ProductList from './ProductList'
import ProductSelect from './ProductSelect'
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

const ProductLib = {
  install: function (Vue) {
    Vue.component('create-product', CreateProduct)
    Vue.component('product-list', ProductList)
    Vue.component('product-select', ProductSelect)
    Vue.use(Element)
  }
}

if (window.Vue) {
  window.ProductLib = ProductLib
  Vue.use(ProductLib)
}

export default ProductLib
