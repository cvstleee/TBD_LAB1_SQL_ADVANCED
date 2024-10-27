<script setup>
import { VApp } from './Utils/vuetifyComponents';
import Navbar from './components/Navbar.vue';
import Home from './components/Home.vue';
</script>

<template>
  <VApp>
    <Navbar />
    <div class="mt-14">
      <Home />
    </div>
  </VApp>
</template>