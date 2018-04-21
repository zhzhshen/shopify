<template>
  <div id="app">
    <a href="http://localhost:9999/oauth/authorize?client_id=shopify&client_secret=client&redirect_uri=http://localhost:3000/login&response_type=code">Login</a>
    <router-view/>
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
