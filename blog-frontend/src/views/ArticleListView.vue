<template>
  <div>
    <div class="card" style="margin-bottom: 16px;">
      <h2>文章列表</h2>
      <div style="display:flex; gap:8px;">
        <input v-model="keyword" class="input" placeholder="输入关键词" />
        <button class="btn" @click="loadArticles">按文章库查询</button>
        <button class="btn" @click="searchWithEs">按ES搜索</button>
      </div>
    </div>

    <ArticleCard v-for="item in list" :key="item.id" :article="item" />
  </div>
</template>

<script>
import { fetchArticlePage, searchArticles } from '../api/article'
import ArticleCard from '../components/ArticleCard.vue'

export default {
  name: 'ArticleListView',
  components: { ArticleCard },
  data() {
    return {
      keyword: '',
      list: []
    }
  },
  mounted() {
    this.loadArticles()
  },
  methods: {
    async loadArticles() {
      const res = await fetchArticlePage({ current: 1, size: 10, keyword: this.keyword })
      if (res.code === 200) {
        this.list = (res.data && res.data.records) || []
      }
    },
    async searchWithEs() {
      const res = await searchArticles({ current: 1, size: 10, keyword: this.keyword })
      if (res.code === 200) {
        this.list = (res.data && res.data.content) || []
      }
    }
  }
}
</script>
