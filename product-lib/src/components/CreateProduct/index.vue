<template>
  <el-form ref="createProductForm" :rules="rules" label-position="left" label-width="80px" :model="form">
    <el-form-item label="产品名称" prop="name">
      <el-input v-model="form.name"/>
    </el-form-item>
    <el-form-item label="产品描述" prop="description">
      <el-input v-model="form.description"/>
    </el-form-item>
    <el-button @click="submit">创建产品</el-button>
  </el-form>
</template>

<script>
import axios from 'axios'

export default {
  name: 'create-product',
  data () {
    return {
      form: {
        name: '',
        description: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入产品名称', trigger: 'blur' },
          { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入产品描述', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submit () {
      this.$refs.createProductForm.validate((valid) => {
        if (valid) {
          this.createProduct()
        } else {
          console.error('error!')
          return false
        }
      })
    },
    createProduct () {
      axios.request({
        url: "http://localhost:8080/shopify/products/",
        method: "post",
        data: {'name': this.form.name, 'description': this.form.description},
        withCredentials:true
      })
        .then(resp => { console.info('success to create product', resp.data) })
        .catch(error => { console.error('error in creating product'); console.log(error) })
    }
  }
}
</script>

<style scoped>

</style>
