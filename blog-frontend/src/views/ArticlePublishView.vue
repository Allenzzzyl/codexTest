<template>
  <div class="card">
    <h2>发布/更新文章</h2>
    <div class="form-grid">
      <input v-model="form.id" class="input" placeholder="文章ID(更新时填写)" />
      <input v-model="form.title" class="input" placeholder="标题" />
      <input v-model="form.summary" class="input" placeholder="摘要" />
      <textarea v-model="form.content" class="textarea" rows="8" placeholder="正文"></textarea>
      <input v-model="form.authorId" class="input" placeholder="作者ID" />
      <select v-model.number="form.status" class="select">
        <option :value="1">已发布</option>
        <option :value="0">草稿</option>
      </select>
      <div style="display:flex; gap: 10px;">
        <button class="btn" @click="create">创建</button>
        <button class="btn" @click="update">更新</button>
      </div>
      <p>{{ message }}</p>
    </div>
  </div>
</template>

<script>
import { createArticle, updateArticle } from '../api/article'

export default {
  name: 'ArticlePublishView',
  data() {
    return {
      form: {
        id: '',
        title: '',
        summary: '',
        content: '',
        authorId: '',
        status: 1
      },
      message: ''
    }
  },
  methods: {
    async create() {
      const payload = {
        title: this.form.title,
        summary: this.form.summary,
        content: this.form.content,
        authorId: Number(this.form.authorId),
        status: this.form.status
      }
      const res = await createArticle(payload)
      this.message = `${res.msg} ${res.data || ''}`
    },
    async update() {
      const payload = {
        id: Number(this.form.id),
        title: this.form.title,
        summary: this.form.summary,
        content: this.form.content,
        status: this.form.status
      }
      const res = await updateArticle(payload)
      this.message = res.msg
    }
  }
}
</script>
