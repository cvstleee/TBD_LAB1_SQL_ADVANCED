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
      <!-- Category select -->
      <label for="category">Categoría:
        <select id="category" v-model="product.categoryId">
          <option disabled value="">Seleccione una categoría</option>
          <option v-for="category in categories" :key="category.id" :value="category.id">
            {{ category.name }}
          </option>
        </select>
      </label>
      <!-- Submit button -->
      <button type="submit">Crear</button>
    </form>
  </div>
</template>
<script setup>

import { ref, onMounted } from 'vue';
import productService from '../services/productService';
import categoryService from '../services/categoryService';

// Definir el objeto product con sus propiedades iniciales
const product = ref({
  name: '',
  description: '',
  price: 0,
  stock: 0,
  state: '',
  category_id: null, 
});

// Definir la lista de productos y categorías
const products = ref([]);
const categories = ref([]); 

// Función para registrar un nuevo producto
const registerProduct = async () => {
  try {
    // Llamar al servicio para crear un nuevo producto
    const response = await productService.postProduct(product.value);
    console.log(response);

    
    products.value.push(response);

   
    product.value = {
      name: '',
      description: '',
      price: 0,
      stock: 0,
      state: '',
      category_id: null, 
    };
  } catch (error) {
    console.error(error.message);
  }
};

onMounted(async () => {
  try {
    const responseProducts = await productService.getProducts();
    products.value = responseProducts; 
    console.log(responseProducts);
    
    const responseCategories = await categoryService.getCategories(); 
    categories.value = responseCategories; 
    console.log(responseCategories);
  } catch (error) {
    console.error(error.message);
  }
});
</script>