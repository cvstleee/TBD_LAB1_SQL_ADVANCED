<template>
  <VAppBar app color="#FF9800">
    <VToolbarTitle>Presta Banco: Créditos Hipotecarios</VToolbarTitle>
    <VSpacer></VSpacer>
    <VBtn to="/registerUser" text>Register Customer</VBtn>
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
  justify-content: flex-start;
}

.container-fluid {
  padding-left: 0;
}

a {
  text-align: center;
  color: white;
  font-weight: 500;
  font-size: 20px;
}
</style>