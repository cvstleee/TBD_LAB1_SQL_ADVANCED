<template>
  <div>
    <h1>Productos</h1>

    <!-- Botones para agregar producto -->
    <div class="button-container">
      <router-link to="/addProduct">
        <button style="background-color: green; color: white;">Agregar Producto</button>
      </router-link>
    </div>

    <div class="button-container" style="text-align: right;">
      <router-link to="/order">
        <button style="background-color: orange; color: white;">Ver Orden</button>
      </router-link>
    </div>

    <!-- Tabla para mostrar productos -->
    <table class="product-table">
      <thead>
        <tr>
          <th>Nombre</th>
          <th>Precio</th>
          <th>Stock</th>
          <th>Unidades a Pedir</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in products" :key="product.id">
          <td>{{ product.name }}</td>
          <td>{{ product.price }}</td>
          <td>{{ product.stock }}</td>
          <td>
            <!-- Contenedor para el campo de entrada y el botón -->
            <div style="display: flex; align-items: center;">
              <!-- Campo de entrada para unidades -->
              <input type="number" v-model.number="product.quantity" min="0" :max="product.stock"
                style="width: 80px; margin-right: 5px;" />
              <!-- Botón para agregar a orden -->
              <button @click="sendProductId(product, product.quantity)"
                :disabled="!isValidQuantity(product.quantity, product.stock)"
                style="background-color: burlywood; color: white;">
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
import { useStore } from 'vuex';

// Definir la lista de productos
const products = ref([]);
const store = useStore();

onMounted(async () => {
  try {
    const responseProducts = await productService.getProducts();
    // Inicializar el campo quantity en 0 para cada producto
    products.value = responseProducts.map((product) => ({
      ...product,
      quantity: 0,
    }));
  } catch (error) {
    console.error(error.message);
  }
});

// Validar si la cantidad ingresada es válida
const isValidQuantity = (quantity, stock) => {
  return quantity > 0 && quantity <= stock;
};

const sendProductId = async (product, cantidad) => {
  cantidad = parseInt(cantidad);

  const newOrderDetails = {
    order_id: store.getters.getOrderId,
    product_id: product.id,
    quantity: cantidad,
    unit_price: product.price,
  };

  if (cantidad > product.stock) {
    alert('No hay suficiente stock');
    return;
  }

  try {
    const response = await orderService.postOrderDetails(newOrderDetails);

    actualizarTotal(newOrderDetails);
    actualizarStock(product, cantidad);
  } catch (error) {
    console.error(error.message);
  }
};

const actualizarTotal = async (newOrderDetails) => {
  const orderID = store.getters.getOrderId;
  const response_order = await orderService.gerOrderById(orderID);

  response_order.total =
    response_order.total + newOrderDetails.quantity * newOrderDetails.unit_price;

  console.log('Response: antes de put', response_order);
  const response = await orderService.putOrder(response_order);
  console.log('Response: despues de put', response);
};

const actualizarStock = async (product, cantidad) => {
  product.stock = product.stock - cantidad;
  const response = await productService.putProduct(product);
  console.log('Response:', response);
};
</script>

<style scoped>
.button-container {
  margin-bottom: 20px;
}

.form-title {
  margin-top: 20px;
}

.product-table {
  width: 100%;
  border-collapse: collapse;
}

.product-table th,
.product-table td {
  border: 1px solid #ccc;
  padding: 10px;
  text-align: left;
}

.product-table th {
  background-color: #f2f2f2;
}
</style>