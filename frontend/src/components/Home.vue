<template>
    <div>
      <Navbar />
      <h1>Productos</h1>
      <ul>
        <li v-for="product in products" :key="product.id">{{ product.name }}</li>
      </ul>
    </div>
  </template>
  
  <script>
  import Navbar from './components/NavBar.vue'; // Asegúrate de que la ruta sea correcta
  import productService from './services/productService';
  
  export default {
    components: {
      Navbar,
    },
    data() {
      return {
        products: [],
      };
    },
    async created() {
      try {
        this.products = await productService.getProducts();
      } catch (error) {
        console.error(error.message);
      }
    },
  };
  </script>
  
  <style scoped>
  /* Estilos específicos para Home.vue */
  body {
    background-color: #f4f4f4; /* Ejemplo de estilo */
  }
  </style>