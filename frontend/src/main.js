import { createApp } from "vue";
import App from "./App.vue";
import vuetify from "./plugins/vuetify";

// Crear la aplicación Vue
const app = createApp(App);

// Usar Vuetify
app.use(vuetify);

// Montar la aplicación en el elemento con id "app"
app.mount("#app");
