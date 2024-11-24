<template>
    <div class="login-container">
        <h1>Iniciar Sesión</h1>
        <form @submit.prevent="login">
            <div>
                <label for="email">Correo:</label>
                <input type="email" v-model="userData.email" required />
            </div>
            <div>
                <label for="password">Contreseña:</label>
                <input type="password" v-model="userData.password" required />
            </div>
            <button type="submit">Iniciar Sesión</button>
        </form>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { loginUser } from '../services/clientService';
import { useStore } from 'vuex';
import { orderService } from '../services/orderService';
import { useRouter } from 'vue-router';

// Definimos las variables reactivas para email y password
const userData = ref({ email: '', password: '' });
const store = useStore();

const router = useRouter();

// Función para manejar el inicio de sesión
const login = async () => {
    const response = await loginUser(userData.value);
    console.log('Response login:', response);
    if (response.status === 200) {
        store.commit('setUser', response.data);
        store.commit('login');
        store.commit('setUserId', response.data.user_id);
        alert('Sesión iniciada correctamente');
        newOrder();console.log('idUser:', );
        router.push('/');
    } else {
        alert('Error al iniciar sesión');
    }
    
    
};

const newOrder = async() => {
    
    const DataNewOrder = {
        "order_date": new Date().toISOString(),
        "state": "Pendiente",
        "client_id":  store.getters.getUserId,
        "total": 0
    }

    const response = await orderService.postOrder(DataNewOrder);
    console.log('Response:', response.id);
    store.commit('setOrderId', response.id);
    console.log('idOrder:', store.getters.getOrderId);
    return response;
}

</script>

<style scoped>
.login-container {
    max-width: 400px;
    margin: 0 auto;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
}

h1 {
    text-align: center;
}

form div {
    margin-bottom: 15px;
}

label {
    display: block;
    margin-bottom: 5px;
}

input {
    width: 100%;
    padding: 8px;
    box-sizing: border-box;
}

button {
    width: 100%;
    padding: 10px;
    background-color: #007BFF;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

button:hover {
    background-color: #0056b3;
}
</style>
