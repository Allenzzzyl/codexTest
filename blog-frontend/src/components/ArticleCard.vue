<template>
  <div class="card article-card">
    <h3>{{ article.title }}</h3>
    <p class="summary">{{ article.summary }}</p>
    <div class="meta">
      <span>作者: {{ article.authorId }}</span>
      <span>状态: {{ article.status }}</span>
      <router-link :to="`/articles/${article.id}`">查看详情</router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ArticleCard',
  props: {
    article: {
      type: Object,
      required: true
    }
  }
}
</script>

<style scoped>
.article-card {
  margin-bottom: 14px;
}

.summary {
  color: #475569;
}

.meta {
  display: flex;
  gap: 10px;
  align-items: center;
  font-size: 13px;
}
</style>
