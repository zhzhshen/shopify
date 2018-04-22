<template>
  <div style="width: 100%;">
    <product-select v-model="productId" style="width: 100%;"/>
    <el-table :data="productPrices" class="product-prices" style="width: 100%">
      <el-table-column prop="productId" label="产品ID" width="80">
      </el-table-column>
      <el-table-column label="产品名称" width="180">
        <template slot-scope="scope">
          {{ getProductInfo('name') }}
        </template>
      </el-table-column>
      <el-table-column prop="price" label="产品价格" width="120">
      </el-table-column>
      <el-table-column label="创建日期">
        <template slot-scope="scope">
          {{ formatDate(scope.row.createdAt) }}
        </template>
      </el-table-column>
      <el-table-column label="生效日期">
        <template slot-scope="scope">
          {{ formatDate(scope.row.effectedAt) }}
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  import axios from 'axios'
  import _ from 'lodash'

  export default {
    name: 'product-prices',
    data() {
      return {
        productId: '',
        product: '',
        productPrices: []
      }
    },
    methods: {
      getProductPriceList() {
        axios.all([
          axios.get(`http://localhost:8080/shopify/products/${this.productId}/`),
          axios.get(`http://localhost:8080/shopify/products/${this.productId}/prices/`)
        ])
          .then(responses => {
            this.product = responses[0].data
            this.productPrices = responses[1].data
          })
          .catch(error => {
            console.error('error in getting product list');
            console.log(error)
          })
      },
      getProductInfo (key) {
        return _.result(this.product, key)
      },
      formatDate(timestamp) {
        return new Date(timestamp).toDateString()
      }
    },
    watch: {
      'productId': 'getProductPriceList'
    }
  }
</script>

<style>
  .product-prices tr th {
    text-align: center;
  }
</style>
