<template>
  <div>
    <h1>Productos</h1>
    
    <!-- Botones para agregar producto -->
    <div class="button-container">
      <router-link to="/addProduct">
        <button style="background-color: green; color: white;">Agregar Producto</button>
      </router-link>
    </div>

    <!-- Tabla para mostrar productos -->
    <table class="product-table">
      <thead>
        <tr>
          <th>Nombre</th>
          <th>Stock</th>
          <th>Estado</th>
          <th>Unidades a Pedir</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in products" :key="product.id">
          <td>{{ product.name }}</td>
          <td>{{ product.stock }}</td> 
          <td>{{ product.state }}</td> 
          <td>
            <!-- Contenedor para el campo de entrada y el botón -->
            <div style="display: flex; align-items: center;">
              <!-- Campo de entrada para unidades -->
              <input 
                type="number" 
                v-model.number="product.quantity" 
                min="1" 
                max="product.stock" 
                placeholder="Cantidad" 
                style="width: 80px; margin-right: 5px;" 
              />
              <!-- Botón para agregar a orden -->
              <button @click="sendProductId(product)" style="background-color: blue; color: white;">
                Agregar a orden de compra
              </button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import productService from '../services/productService';
import { orderService } from '../services/orderService';

// Definir la lista de productos
const products = ref([]); 

onMounted(async () => {
  try {
    const responseProducts = await productService.getProducts();
    products.value = responseProducts; 
  } catch (error) {
    console.error(error.message);
  }
});

const sendProductId = async (product) => {
  const newOrderDetails = {
    order_id: 4,
    product_id: product.id,
    quantity: 1,
    unit_price: product.price,
  };

  try {
    const response = await orderService.postOrderDetails(newOrderDetails);
    console.log(response);
  } catch (error) {
    console.error(error.message);
  }
}
</script>

<style scoped>
.button-container {
  margin-bottom: 20px; 
}

.form-title{
  margin-top: 20px;
}
.product-table {
  width: 100%; 
  border-collapse: collapse; 
}

.product-table th, .product-table td {
  border: 1px solid #ccc; 
  padding: 10px; 
  text-align: left; 
}

.product-table th {
  background-color: #f2f2f2; 
}
</style>