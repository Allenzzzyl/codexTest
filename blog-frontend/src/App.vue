<template>
  <div class="app-shell">
    <header class="topbar">
      <h1 class="brand">My Blog Platform</h1>
      <nav>
        <router-link to="/">首页</router-link>
        <router-link to="/publish">发布文章</router-link>
        <router-link to="/login">登录</router-link>
        <router-link to="/register">注册</router-link>
      </nav>
    </header>
    <main class="main-content">
      <router-view />
    </main>
  </div>
</template>

<style scoped>
.app-shell {
  min-height: 100vh;
  background: linear-gradient(180deg, #f6f8fb 0%, #edf1f7 100%);
}

.topbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 24px;
  background: #1f2937;
  color: #fff;
}

.brand {
  margin: 0;
  font-size: 20px;
}

nav {
  display: flex;
  gap: 12px;
}

nav a {
  color: #cbd5e1;
  text-decoration: none;
}

nav a.router-link-exact-active {
  color: #fff;
  font-weight: 600;
}

.main-content {
  max-width: 1080px;
  margin: 0 auto;
  padding: 20px;
}
</style>
