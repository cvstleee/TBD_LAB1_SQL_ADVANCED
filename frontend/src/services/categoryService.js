import httpClient from "../Utils/http-common"; 


const categoryService = {
    async getCategories() {
      try {
        const response = await httpClient.get("api/v1/categories");
        return response.data;
      } catch (error) {
        throw new Error(
          error.response ? error.response.data : "Error al obtener categorias"
        );
      }
    },
  
    async getCategory(id) {
      try {
        const response = await httpClient.get(`${API_URL}/${id}`);
        return response.data;
      } catch (error) {
        throw new Error(
          error.response ? error.response.data : "Categoria no encontrado"
        );
      }
    },
  };
  
  export default categoryService;