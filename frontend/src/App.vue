<script setup>
import { VApp } from './Utils/vuetifyComponents';
import Navbar from './components/NavBar.vue';
import { RouterView } from 'vue-router';


</script>

<template>

  <VApp>
    <Navbar />
    <div class="mt-14">
      <RouterView />
    </div>
  </VApp>

</template>