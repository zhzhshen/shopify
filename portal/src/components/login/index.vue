<template>
</template>

<script>
  import _ from 'lodash';
  import axios from 'axios';

  export default {
    mounted() {
      const code = _.result(this.$route, 'query.code')
      if (!_.isEmpty(code)) {
        axios.request({
          method: "post",
          url: `http://localhost:9999/oauth/token?code=${code}&client_id=shopify&client_secret=shopify&grant_type=authorization_code&redirect_uri=http://localhost:3000/login`,
          withCredentials: true
        })
          .then(resp => {
            console.log(resp.data.access_token)
            this.$store.dispatch('updateToken', resp.data.access_token)
          })
          .catch(err => console.log(err))
      }
    }
  }
</script>
