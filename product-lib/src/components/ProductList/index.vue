<template>
  <el-table :data="products" class="product-list" style="width: 100%">
    <el-table-column prop="name" label="产品名称">
    </el-table-column>
    <el-table-column prop="description" label="产品描述">
    </el-table-column>
  </el-table>
</template>

<script>
import Vue from 'vue'
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'
Vue.use(Element)

export default {
  name: 'product-list',
  data () {
    return {
      products: []
    }
  },
  created () {
    this.getProductList()
  },
  methods: {
    getProductList () {
      axios.get('http://localhost:8080/products/')
        .then(resp => { this.products = resp.data })
        .catch(error => { console.error('error in getting product list'); console.log(error) })
    }
  }
}
</script>

<style>
.product-list th {
  text-align: center;
}
</style>
