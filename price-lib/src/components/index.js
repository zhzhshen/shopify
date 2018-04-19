import CreateProductPrice from './CreateProductPrice'
import ProductPrices from './ProductPrices'
import ProductLib from 'product-lib'
import 'product-lib/dist/product-lib.min.css'
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

const PriceLib = {
  install: function (Vue) {
    Vue.component('create-product-price', CreateProductPrice)
    Vue.component('product-prices', ProductPrices)
    Vue.use(ProductLib);
    Vue.use(Element)
  }
}

if (window.Vue) {
  window.PriceLib = PriceLib
  Vue.use(PriceLib)
}

export default PriceLib
