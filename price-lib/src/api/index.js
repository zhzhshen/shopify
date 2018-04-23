import axios from 'axios'

const createProductPrice = function (productId, price, effectedAt) {
  return axios.request({
    url: `http://localhost:8080/shopify/products/${productId}/prices/`,
    method: "post",
    xsrfCookieName: 'XSRF-TOKEN',
    xsrfHeaderName: 'X-XSRF-TOKEN',
    data: {
      'price': price,
      'effectedAt': effectedAt
    },
    headers: {
      'Content-Type':'application/x-www-form-urlencoded'
    },
    withCredentials: true
  })
}

const getProductList = function (productId) {
  return axios.request({
    url: `http://localhost:8080/shopify/products/${productId}/`,
    method: "get",
    withCredentials: true
  })
}

const getProductPriceList = function (productId) {
  return axios.request({
    url: `http://localhost:8080/shopify/products/${productId}/prices/`,
    method: "get",
    withCredentials: true
  })
}

export {createProductPrice, getProductList, getProductPriceList}
