import CreateProductComponent from './CreateProduct'
import ProductListComponent from './ProductList'
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

const ProductLib = {
  install: function (Vue) {
    Vue.component('create-product', CreateProductComponent)
    Vue.component('product-list', ProductListComponent)
    Vue.use(Element)
  }
}

if (window.Vue) {
  window.ProductLib = ProductLib
  Vue.use(ProductLib)
}

export default ProductLib
