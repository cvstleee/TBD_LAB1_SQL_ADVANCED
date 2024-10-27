// src/services/productService.js

import axios from 'axios';

const API_URL = 'http://tu-backend.com/api/v1/products'; // Cambia esta URL según tu configuración

const productService = {
    async getProducts() {
        try {
            const response = await axios.get(API_URL);
            return response.data;
        } catch (error) {
            throw new Error(error.response ? error.response.data : 'Error al obtener productos');
        }
    },

    async getProduct(id) {
        try {
            const response = await axios.get(`${API_URL}/${id}`);
            return response.data;
        } catch (error) {
            throw new Error(error.response ? error.response.data : 'Producto no encontrado');
        }
    },

    async postProduct(product) {
        try {
            const response = await axios.post(API_URL, product);
            return response.data;
        } catch (error) {
            throw new Error(error.response ? error.response.data : 'Error al crear producto');
        }
    },

    async putProduct(id, product) {
        try {
            const response = await axios.put(`${API_URL}/${id}`, product);
            return response.data;
        } catch (error) {
            throw new Error(error.response ? error.response.data : 'Error al actualizar producto');
        }
    },

    async deleteProduct(id) {
        try {
            await axios.delete(`${API_URL}/${id}`);
            return true; // Retorna true si se elimina correctamente
        } catch (error) {
            throw new Error(error.response ? error.response.data : 'Error al eliminar producto');
        }
    }
};

export default productService;