<template>
  <VAppBar app color="#FF9800">
    <VBtn to="/">
      <VToolbarTitle>Gestión de Productos</VToolbarTitle>
    </VBtn>
    <VSpacer></VSpacer>
    <div class="login-button" v-if="!loginState">
      <VBtn to="/registerUser" text>Registrar usuario</VBtn>
      <VBtn to="/login" text>Ingresar</VBtn>
      <VBtn icon @click="toggleDrawer(true)">
        <VIcon>mdi-menu</VIcon>
      </VBtn>
    </div>
    <div class="logout-button" v-else>
      <VBtn to="/logs" text>
        Bitacora
      </VBtn>
      <VBtn v-on:click="logout" to="/login" text>Cerrar secion</VBtn>
    </div>
  </VAppBar>
</template>

<script setup>
import { VAppBar, VToolbarTitle, VSpacer, VBtn, VIcon } from '../Utils/vuetifyComponents';
import { useStore } from 'vuex';
import { computed } from 'vue';
import { logoutUser } from '../services/clientService';

const store = useStore();
const loginState = computed(() => store.getters.getLogin);

const logout = async () => {
  const response = await logoutUser();

  if (response.status === 200) {
    alert("Usuario desconectado");
    store.commit("logout");
    store.commit("clearUser");
  } else {
    alert("Error al desconectar");
  }
}

</script>

<style scoped>
.navbar {
  background-color: #333;
  display: flex;
  /* Asegura que la barra de navegación use flexbox */
  justify-content: flex-start;
  /* Alinea el contenido al inicio */
  padding: 10px;
  /* Agrega un poco de espaciado interno */
}

.container-fluid {
  padding-left: 0;
  /* Elimina el espaciado a la izquierda */
}

a {
  color: white;
  /* Color del texto de los enlaces */
  font-weight: 500;
  /* Peso de la fuente */
  font-size: 20px;
  /* Tamaño de la fuente */
  text-decoration: none;
  /* Elimina el subrayado de los enlaces */
  margin-right: 15px;
  /* Espacio entre los enlaces */
}

a:hover {
  text-decoration: underline;
  /* Añade un subrayado al pasar el mouse */
}
</style>
