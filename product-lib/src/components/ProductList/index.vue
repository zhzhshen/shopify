<template>
  <el-table :data="products" class="product-list" style="width: 100%">
    <el-table-column prop="name" label="产品名称">
    </el-table-column>
    <el-table-column prop="description" label="产品描述">
    </el-table-column>
  </el-table>
</template>

<script>
import axios from 'axios'

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
      axios.get('http://localhost:8080/shopify/products/?access_token=aaf1d0ec-552d-4806-9724-03e305d4d3d7')
        .then(resp => { this.products = resp.data })
        .catch(error => { console.error('error in getting product list'); console.log(error) })
    }
  }
}
</script>

<style>
.product-list tr th {
  text-align: center;
}
</style>
