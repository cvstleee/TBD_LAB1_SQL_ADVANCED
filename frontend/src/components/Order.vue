<template>
    <div>
      <h1>Orden de Compra Actual</h1>
      <table class="table">
        <thead>
          <tr>
            <th>Nombre del Producto</th>
            <th>Cantidad</th>
            <th>Precio unitario</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(product, index) in products" :key="index">
            <td>{{ product.name }}</td>
            <td>{{ product.quantity }}</td>
            <td>{{ product.unit_price.toFixed(2) }} pesos</td>
          </tr>
        </tbody>
      </table>
    <div>
        <div style="text-align: center;">
          <h2>Total: {{ total }} pesos</h2>
        </div>
    </div>    
<div style="text-align: center; margin-top: 20px;">
    <button @click="confirmarOrder" style="background-color: green; color: white; padding: 10px 20px; border: none; border-radius: 5px;">Confirmar Orden</button>
</div>
  
      <div v-if="loading" class="loader">Cargando...</div>
      <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>
    </div>
</template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import axios from 'axios';
  import productService from '../services/productService';
  import { orderService } from '../services/orderService';
  import { useStore } from 'vuex';
  
  const products = ref([]);
  const loading = ref(true);
  const errorMessage = ref('');
  const total = ref(0);
  const store = useStore();
  
  const obtenerOrder = async () => {
  try {
    const orderID = store.getters.getOrderId;

    const response_order = await orderService.gerOrderById(orderID); 

    total.value = response_order.total;

    
    const response = await orderService.getProductOrdersById(orderID); // Ajusta según tu API
    
    const orderData = response; // Asumiendo que response.data contiene la orden

    // Extraer los product_ids de la orden
    const productIds = orderData.map(order => order.product_id);
    // Hacer solicitudes para cada product_id
    const productRequests = productIds.map(id => productService.getProduct(id));
    // Esperar todas las respuestas
    const productsResponses = await Promise.all(productRequests);
    
    console.log('Solicitudes de productos:', productsResponses);

    products.value = productsResponses.map((res, index) => ({
      name: res.name || 'Nombre no disponible',
      quantity: orderData[index].quantity,
      unit_price: orderData[index].unit_price,
    }));
    
  } catch (error) {
    errorMessage.value = 'Error al obtener productos: ' + error.message;
  } finally {
    loading.value = false;
  }
};

  onMounted(obtenerOrder);
  </script>
  
  <style scoped>
  .loader {
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .error-message {
    color: red;
  }
  .table {
    width: 100%;
    border-collapse: collapse;
  }
  .table th, .table td {
    border: 1px solid #ddd;
    padding: 8px;
  }
  .table th {
    background-color: #f2f2f2;
  }
  </style>