import axios from 'axios'

const createProduct = function (name, description) {
  return axios.request({
    url: "http://localhost:8080/shopify/products/",
    method: "post",
    data: {'name': name, 'description': description},
    headers: {
      'Content-Type':'application/x-www-form-urlencoded'
    },
    withCredentials: true
  })
}

const getProductList = function () {
  return axios.request({
    url: "http://localhost:8080/shopify/products/",
    method: "get",
    withCredentials: true
  })
}

export {createProduct, getProductList}
