import { createApp } from "vue";
import App from "./App.vue";
import vuetify from "./plugins/vuetify"; // Asegúrate de que el archivo de plugins esté configurado
import store from "./store/Store";
import router from "./routers/routers";

createApp(App)
  .use(vuetify) // Agrega Vuetify aquí
  .use(router)
  .use(store)
  .mount("#app");
