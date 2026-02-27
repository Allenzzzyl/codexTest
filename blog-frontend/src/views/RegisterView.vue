<template>
  <div class="card">
    <h2>注册</h2>
    <div class="form-grid">
      <input v-model="form.username" class="input" placeholder="用户名" />
      <input v-model="form.email" class="input" placeholder="邮箱" />
      <input v-model="form.password" class="input" type="password" placeholder="密码" />
      <button class="btn" @click="submit">注册</button>
      <p>{{ message }}</p>
    </div>
  </div>
</template>

<script>
import { register } from '../api/user'

export default {
  name: 'RegisterView',
  data() {
    return {
      form: {
        username: '',
        email: '',
        password: ''
      },
      message: ''
    }
  },
  methods: {
    async submit() {
      const res = await register(this.form)
      this.message = res.msg
    }
  }
}
</script>
