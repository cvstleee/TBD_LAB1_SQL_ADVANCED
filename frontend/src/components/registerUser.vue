<template>
    <div class="register-container">
        <h1>Registro de Nuevo Usuario</h1>
        <form @submit.prevent="registerUser">
            <div>
                <label for="name">Nombre:</label>
                <input type="text" id="name" v-model="userData.name" required />
            </div>
            <div>
                <label for="address">Dirección:</label>
                <input type="text" id="address" v-model="userData.address" required />
            </div>
            <div>
                <label for="email">Correo:</label>
                <input type="email" id="email" v-model="userData.email" required />
            </div>
            <div>
                <label for="password">Contraseña:</label>
                <input type="password" id="password" v-model="userData.password" required />
            </div>
            <div>
                <label for="phone">Teléfono:</label>
                <input type="tel" id="phone" v-model="userData.phone" required />
            </div>
            <button type="submit">Registrar</button>
        </form>
    </div>
</template>

<script setup>
import { reactive } from 'vue';
import { postClient } from '../services/clientService';

// Definimos el objeto reactivo para el usuario
const userData = reactive({
    name: '',
    address: '',
    email: '',
    password: '',
    phone: ''
});

// Función para manejar el registro del usuario
const registerUser = async () => {
    const response = await postClient(userData);
    if (response.status === 201) {
        alert('Usuario registrado correctamente');
    } else {
        alert('Error al registrar el usuario');
    }
};
</script>

<style scoped>
.register-container {
    max-width: 400px;
    margin: 0 auto;
    /* Centra el contenedor */
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
}

h1 {
    text-align: center;
    /* Centra el texto del título */
    color: green;
}

form div {
    margin-bottom: 15px;
    /* Espaciado entre los campos */
}

label {
    display: block;
    /* Asegura que las etiquetas estén en línea separada */
    margin-bottom: 5px;
    /* Espaciado entre la etiqueta y el campo */
}

input {
    width: 100%;
    padding: 8px;
    box-sizing: border-box;
    /* Incluye padding en el ancho total */
}

button {
    width: 100%;
    padding: 10px;
    background-color: green;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

button:hover {
    background-color: darkgreen;
}
</style>