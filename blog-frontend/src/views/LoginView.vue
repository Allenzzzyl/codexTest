<template>
  <div class="card">
    <h2>登录</h2>
    <div class="form-grid">
      <input v-model="form.username" class="input" placeholder="用户名" />
      <input v-model="form.password" class="input" type="password" placeholder="密码" />
      <button class="btn" @click="submit">登录</button>
      <p>{{ message }}</p>
    </div>
  </div>
</template>

<script>
import { login } from '../api/user'

export default {
  name: 'LoginView',
  data() {
    return {
      form: {
        username: '',
        password: ''
      },
      message: ''
    }
  },
  methods: {
    async submit() {
      const res = await login(this.form)
      this.message = res.msg
      if (res.code === 200 && res.data) {
        localStorage.setItem('token', res.data)
      }
    }
  }
}
</script>
