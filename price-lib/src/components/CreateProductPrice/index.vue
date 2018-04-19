<template>
  <el-form ref="createProductPriceForm" :rules="rules" label-position="left" label-width="120px" :model="form" style="width: 400px;">
    <el-form-item label="产品" prop="productId">
      <product-select v-model="form.productId" style="width: 100%;"/>
    </el-form-item>
    <el-form-item label="产品价格" prop="price">
      <el-input v-model="form.price" style="width: 100%;"/>
    </el-form-item>
    <el-form-item label="价格生效日期" prop="effectedAt">
      <el-date-picker style="width: 100%;"
        v-model="form.effectedAt"
        type="date"
        placeholder="选择日期">
      </el-date-picker>
    </el-form-item>
    <el-button @click="submit">创建产品价格</el-button>
  </el-form>
</template>

<script>
  import axios from 'axios'

  export default {
    name: 'create-product-price',
    data() {
      return {
        form: {
          productId: '',
          price: '',
          effectedAt: ''
        },
        rules: {
          productId: [
            {required: true, message: '请选择产品', trigger: 'blur'}
          ],
          price: [
            {required: true, message: '请输入产品价格', trigger: 'blur'}
          ],
          effectedAt: [
            {required: true, message: '请输入生效日期', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      submit() {
        this.$refs.createProductPriceForm.validate((valid) => {
          if (valid) {
            this.createProductPrice()
          } else {
            console.error('error!')
            return false
          }
        })
      },
      createProductPrice() {
        axios.post(`http://localhost:8080/products/${this.form.productId}/prices/`,
          {
            'price': this.form.price,
            'effectedAt': this.form.effectedAt
          })
          .then(resp => {
            console.info('success to create product price', resp.data)
          })
          .catch(error => {
            console.error('error in creating product price');
            console.log(error)
          })
      }
    }
  }
</script>

<style scoped>

</style>
