<template>
  <div>
    <h1>Productos</h1>
    <ul>
      <li v-for="product in products" :key="product.id">{{ product.name }}</li>
    </ul>
  </div>
  <div>
    <form @submit.prevent="registerProduct">
      <!-- Name input -->
      <label for="name">Nombre:
        <input type="text" id="name" name="name" v-model="product.name" />
      </label>
      <!-- Description input -->
      <label for="description">Descripción:
        <input type="text" id="description" name="description" v-model="product.description" />
      </label>
      <!-- Price input -->
      <label for="price">Precio:
        <input type="number" id="price" name="price" v-model.number="product.price" />
      </label>
      <!-- Stock input -->
      <label for="stock">Stock:
        <input type="number" id="stock" name="stock" v-model.number="product.stock" />
      </label>
      <!-- State input -->
      <label for="state">Estado:
        <input type="text" id="state" name="state" v-model="product.state" />
      </label>
      <!-- Submit button -->
      <button type="submit">Crear</button>
    </form>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import productService from '../services/productService';

// Definir el objeto product con sus propiedades iniciales
const product = ref({
  name: '',
  description: '',
  price: 0,
  stock: 0,
  state: '',
});

// Definir la lista de productos
const products = ref([]);

// Función para registrar un nuevo producto
const registerProduct = async () => {
  try {
    // Llamar al servicio para crear un nuevo producto
    const response = await productService.postProduct(product.value);
    console.log(response);

    // Agregar el nuevo producto a la lista de productos
    products.value.push(response);

    // Limpiar el formulario después de enviar los datos
    product.value = {
      name: '',
      description: '',
      price: 0,
      stock: 0,
      state: '',
    };
  } catch (error) {
    console.error(error.message);
  }
};

// Cargar los productos existentes cuando se monta el componente
onMounted(async () => {
  try {
    const response = await productService.getProducts();
    products.value = response; // Asignar los productos obtenidos a la lista
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