import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
// import vueJsx from '@vitejs/plugin-vue-jsx'

// import vuetify from 'vite-plugin-vuetify'

// import vuetify from './src/plugins/vuetify'
import vuetify from 'vite-plugin-vuetify';



// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(), // Enabled by defaul
    vuetify(),
  ],
  css: true,
  exports: {
    transpileDependencies: ["vuetify"],
  },
  server: {
    port: 8081,
  },
  build: {
    transpile: ['vuetify'],
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  }
})
