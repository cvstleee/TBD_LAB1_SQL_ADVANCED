<template>
    <div class="register-container">
        <h1>Registro de Nuevo Usuario</h1>
        <form @submit.prevent="registerUser">
            <div>
                <label for="name">Nombre:</label>
                <input type="text" id="name" v-model="user.name" required />
            </div>
            <div>
                <label for="address">Dirección:</label>
                <input type="text" id="address" v-model="user.address" required />
            </div>
            <div>
                <label for="email">Email:</label>
                <input type="email" id="email" v-model="user.email" required />
            </div>
            <div>
                <label for="password">Contraseña:</label>
                <input type="password" id="password" v-model="user.password" required />
            </div>
            <div>
                <label for="phone">Teléfono:</label>
                <input type="tel" id="phone" v-model="user.phone" required />
            </div>
            <button type="submit">Registrar</button>
        </form>
    </div>
</template>

<script>
export default {
    name: 'RegisterUser',
    data() {
        return {
            user: {
                name: '',
                address: '',
                email: '',
                password: '',
                phone: ''
            }
        };
    },
    methods: {
        registerUser() {
            // Aquí puedes manejar el registro del usuario
            console.log('Usuario registrado:', this.user);
        }
    }
}
</script>

<style scoped>
.register-container {
    max-width: 400px;
    margin: 0 auto; /* Centra el contenedor */
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
}

h1 {
    text-align: center; /* Centra el texto del título */
    color: green;
}

form div {
    margin-bottom: 15px; /* Espaciado entre los campos */
}

label {
    display: block; /* Asegura que las etiquetas estén en línea separada */
    margin-bottom: 5px; /* Espaciado entre la etiqueta y el campo */
}

input {
    width: 100%;
    padding: 8px;
    box-sizing: border-box; /* Incluye padding en el ancho total */
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
