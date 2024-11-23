import httpClient from "../Utils/http-common";

export const postClient = async (userData) => {
  try {
    const response = await httpClient.post("auth/register", userData);
    return { data: response.data, status: response.status };
  } catch (error) {
    // Manejo de errores
    if (error.response) {
      // El servidor respondió con un código de estado fuera del rango 2xx
      console.error("Error en la respuesta del servidor:", error.response.data);
      return { data: error.response.data, status: error.response.status };
    } else if (error.request) {
      // La solicitud fue hecha pero no se recibió respuesta
      console.error("No se recibió respuesta del servidor:", error.request);
    } else {
      // Algo salió mal al configurar la solicitud
      console.error("Error al hacer la solicitud:", error.message);
    }
    throw error; // Lanza el error para que pueda ser manejado donde se llame esta función
  }
};

export const loginUser = async (userData) => {
  try {
    const response = await httpClient.post("auth/login", userData);
    return { data: response.data, status: response.status };
  } catch (error) {
    // Manejo de errores
    if (error.response) {
      // El servidor respondió con un código de estado fuera del rango 2xx
      console.error("Error en la respuesta del servidor:", error.response.data);
      return { data: error.response.data, status: error.response.status };
    } else if (error.request) {
      // La solicitud fue hecha pero no se recibió respuesta
      console.error("No se recibió respuesta del servidor:", error.request);
    } else {
      // Algo salió mal al configurar la solicitud
      console.error("Error al hacer la solicitud:", error.message);
    }
    throw error; // Lanza el error para que pueda ser manejado donde se llame esta función
  }
};

export const logoutUser = async () => {
  try {
    const response = await httpClient.post("auth/logout");
    return { data: response.data, status: response.status };
  } catch (error) {
    // Manejo de errores
    if (error.response) {
      // El servidor respondió con un código de estado fuera del rango 2xx
      console.error("Error en la respuesta del servidor:", error.response.data);
      return { data: error.response.data, status: error.response.status };
    } else if (error.request) {
      // La solicitud fue hecha pero no se recibió respuesta
      console.error("No se recibió respuesta del servidor:", error.request);
    } else {
      // Algo salió mal al configurar la solicitud
      console.error("Error al hacer la solicitud:", error.message);
    }
    throw error; // Lanza el error para que pueda ser manejado donde se llame esta función
  }
};
