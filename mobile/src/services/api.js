import axios from 'axios';

const api = axios.create({
  baseURL: 'https://stocky-api.herokuapp.com'
});

export default api;