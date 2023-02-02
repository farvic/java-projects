import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router'

import vuetify from './plugins/vuetify';

// Vue.config.productionTip = false


createApp(App).use(router,vuetify).mount('#app')

// new Vue({
//   router,
//   vuetify,
//   render: h => h(App)
// }).$mount('#app')
