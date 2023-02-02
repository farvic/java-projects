import { createApp } from "vue";
import App from "./App.vue";
import router from "./router/index";
// import { createVuetify } from "vuetify";
import vuetify from "./plugins/vuetify";
// import './main.scss'

import 'vuetify/styles'



// import "./assets/main.css";

// const vuetify = createVuetify({components, directives});

const app = createApp(App);

app.use(router);
app.use(vuetify);

app.mount("#app");
