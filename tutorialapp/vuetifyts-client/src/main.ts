import { createApp } from "vue";
import { createPinia } from "pinia";
// import * as components from 'vuetify/components';
// import { VDataTable } from 'vuetify/labs/VDataTable';
import vuetify from "./plugins/vuetify"
import 'vuetify/styles'
import App from "./App.vue";
import router from "@/router";
// import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'

// import 'vuetify/dist/vuetify.min.css';
// import "./assets/main.css";



const app = createApp(App);
// app.component('VDataTable', VDataTable);
app.use(createPinia());
app.use(router);
app.use(vuetify);
// app.use(components);



app.mount("#app");
