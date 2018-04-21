import axios from 'axios'
import _ from 'lodash'

axios.interceptors.request.use(function(config){
  config.params = _.assign(config.params, {'access_token': 'e66bc0c3f103e474e34f29fb3f1c970547b6e1a5'})
  return config;
},function(error){
  return Promise.reject(error);
});

axios.interceptors.response.use(function(response){
  return response;
},function(error){
  return Promise.reject(error);
});

export default axios
