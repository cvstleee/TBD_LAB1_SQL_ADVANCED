import httpClient from "../Utils/http-common"; // Cambia esta URL según tu configuración

export const orderService = {

    async getProductOrdersById(id) {
        try {
            const response = await httpClient.get(`/api/v1/ordersDetails/order/${id}`);
            return response.data;
        } catch (error) {
            throw new Error(
                error.response ? error.response.data : "Error al obtener ordenes"
            );
        }
    },

    async gerOrderById(id) {
        try {
            const response = await httpClient.get(`/api/v1/orders/${id}`);
            return response.data;
        } catch (error) {
            throw new Error(
                error.response ? error.response.data : "Error al obtener orden"
            );
        }
    },


    async postOrderDetails(order) {
        try {
            const response = await httpClient.post("/api/v1/ordersDetails", order);
            return response.data;
        } catch (error) {
            throw new Error(
                error.response ? error.response.data : "Error al crear orden"
            );
        }
    },


    async postOrder(order) {
        console.log(order);
        try {
            const response = await httpClient.post("/api/v1/orders", order);
            return response.data;
        } catch (error) {
            throw new Error(
                error.response ? error.response.data : "Error al crear orden"
            );
        }
    },


    async putOrder(order) {
        console.log(order);
        try {
            const response = await httpClient.put(`/api/v1/orders/${order.id}`, order);
            return response.data;
        } catch (error) {
            throw new Error(
                error.response ? error.response.data : "Error al actualizar orden"
            );
        }
    },

}




