import Vue from 'vue'
import Router from 'vue-router'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import ArticleListView from '../views/ArticleListView.vue'
import ArticleDetailView from '../views/ArticleDetailView.vue'
import ArticlePublishView from '../views/ArticlePublishView.vue'

Vue.use(Router)

export default new Router({
  mode: 'hash',
  routes: [
    { path: '/', component: ArticleListView },
    { path: '/articles/:id', component: ArticleDetailView },
    { path: '/publish', component: ArticlePublishView },
    { path: '/login', component: LoginView },
    { path: '/register', component: RegisterView }
  ]
})
