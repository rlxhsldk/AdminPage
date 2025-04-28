import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  server: {
    proxy: {
      '/user': {
        target: 'http://localhost:8000',  // API 서버 주소
        changeOrigin: true,  // 원본 출처를 변경하여 요청 보내기
        secure: false,  // HTTPS를 사용할 경우 설정
      },
    },
  },
})