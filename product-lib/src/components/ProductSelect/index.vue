<template>
  <el-select :value="value" @change="updateValue" placeholder="请选择产品" style="width: 100%;">
    <el-option
      v-for="item in products"
      :key="item.id"
      :label="item.name"
      :value="item.id">
    </el-option>
  </el-select>
</template>

<script>
import axios from 'axios'

export default {
  name: 'product-select',
  props: ['value'],
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
      axios.request({
        url: "http://localhost:8080/products/",
        method: "get",
        withCredentials:true
      })
      // axios.get('http://localhost:8080/products/')
        .then(resp => { this.products = resp.data })
        .catch(error => { console.error('error in getting product list'); console.log(error) })
    },
    updateValue (value) {
      this.$emit('input', value);
    }
  }
}
</script>
