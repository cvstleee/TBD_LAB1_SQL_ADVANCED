import axios from "axios";
import { ACCESS_TOKEN } from "./Constants";
import { useCookies } from "vue3-cookies";

const { cookies } = useCookies();

const backendServer = import.meta.env.VITE_BACKEND_SERVER;
const backendPort = import.meta.env.VITE_BACKEND_PORT;

axios.defaults.withCredentials = true;

export default axios.create({
  baseURL: `http://${backendServer}:${backendPort}`,
  headers: {
    "Content-Type": "application/json",
  },
});

axios.interceptors.request.use(
  (config) => {
    const token = cookies.get(ACCESS_TOKEN);
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);
