<template>
  <VAppBar app color="#FF9800">
    <VBtn to="/">
      <VToolbarTitle>E- Commerce</VToolbarTitle>
    </VBtn>
    <VSpacer></VSpacer>
    <VBtn to="/registerUser" text>Register Customer</VBtn>
    <VBtn to="/login" text>Login</VBtn>
    <VBtn icon @click="toggleDrawer(true)">
      <VIcon>mdi-menu</VIcon>
    </VBtn>
  </VAppBar>
</template>

<script setup>
import { VAppBar, VToolbarTitle, VSpacer, VBtn, VIcon } from '../Utils/vuetifyComponents';
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
