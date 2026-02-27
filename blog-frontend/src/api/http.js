import axios from 'axios'

const http = axios.create({
  timeout: 10000
})

const serviceMap = {
  user: 'http://localhost:8081',
  article: 'http://localhost:8082',
  search: 'http://localhost:8083'
}

export function request(service, config) {
  return http({
    baseURL: serviceMap[service],
    ...config
  }).then(res => res.data)
}
