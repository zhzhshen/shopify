<template>
  <div id="app">
    <img src="./assets/logo.png">
    <div style="display: flex;">
      <div style="width: 50%;">
        <create-product/>
      </div>
      <div style="width: 50%;">
        <product-list/>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'App'
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
