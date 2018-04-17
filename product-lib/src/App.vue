<template>
  <div id="app">
    <img src="./assets/logo.png">
    <div style="display: flex;">
      <div style="width: 50%;">
        <CreateProduct/>
      </div>
      <div style="width: 50%;">
        <ProductList/>
      </div>
    </div>
  </div>
</template>

<script>
import CreateProduct from './components/CreateProduct/index'
import ProductList from './components/ProductList/index'

export default {
  name: 'App',
  components: {
    CreateProduct, ProductList
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
