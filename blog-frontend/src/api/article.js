import { request } from './http'

export function fetchArticlePage(params) {
  return request('article', {
    url: '/api/articles',
    method: 'get',
    params
  })
}

export function fetchArticleDetail(id) {
  return request('article', {
    url: `/api/articles/${id}`,
    method: 'get'
  })
}

export function createArticle(data) {
  return request('article', {
    url: '/api/articles',
    method: 'post',
    data
  })
}

export function updateArticle(data) {
  return request('article', {
    url: '/api/articles',
    method: 'put',
    data
  })
}

export function deleteArticle(id) {
  return request('article', {
    url: `/api/articles/${id}`,
    method: 'delete'
  })
}

export function searchArticles(params) {
  return request('search', {
    url: '/api/search/articles',
    method: 'get',
    params
  })
}
