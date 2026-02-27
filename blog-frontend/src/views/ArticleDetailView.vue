<template>
  <div class="card" v-if="detail">
    <h2>{{ detail.title }}</h2>
    <p>作者: {{ detail.authorId }} | 状态: {{ detail.status }}</p>
    <p>{{ detail.summary }}</p>
    <hr />
    <article>{{ detail.content }}</article>
  </div>
</template>

<script>
import { fetchArticleDetail } from '../api/article'

export default {
  name: 'ArticleDetailView',
  data() {
    return {
      detail: null
    }
  },
  async mounted() {
    const res = await fetchArticleDetail(this.$route.params.id)
    if (res.code === 200) {
      this.detail = res.data
    }
  }
}
</script>
