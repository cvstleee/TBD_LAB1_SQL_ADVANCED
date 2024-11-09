<template>
  <div>
    <h1>Productos</h1>
    <ul>
      <li v-for="product in products" :key="product.id">{{ product.name }}</li>
    </ul>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import productService from '../services/productService';

const products = ref([]);

onMounted(async () => {
  try {
    const response = await productService.getProducts();
    console.log(response);
  } catch (error) {
    console.error(error.message);
  }
});
</script>

<style scoped>
body {
  background-color: #f4f4f4;
}
</style>