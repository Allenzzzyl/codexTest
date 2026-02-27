import { defineConfig } from 'vite'
import legacy from '@vitejs/plugin-legacy'
import { createVuePlugin } from 'vite-plugin-vue2'

export default defineConfig({
  plugins: [createVuePlugin(), legacy()],
  server: {
    port: 5173,
    host: '0.0.0.0'
  }
})
