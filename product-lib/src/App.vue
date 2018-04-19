<template>
  <div id="app">
    <img src="./assets/logo.png">
    <div style="display: flex;">
      <div style="width: 30%;">
        <create-product/>
      </div>
      <div style="width: 30%;">
        <product-list/>
      </div>
      <div style="width: 30%;">
        <product-select v-model="productId"/>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'App',
  data () {
    return {
      productId: ''
    }
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
