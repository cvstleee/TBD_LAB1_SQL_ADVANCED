import { createApp } from "vue";
import App from "./App.vue";
import vuetify from "./plugins/vuetify"; // Asegúrate de que el archivo de plugins esté configurado

import router from "./routers/routers";

createApp(App)
  .use(vuetify) // Agrega Vuetify aquí
  .use(router)
  .mount("#app");
