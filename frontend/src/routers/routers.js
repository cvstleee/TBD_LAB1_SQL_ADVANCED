import { createRouter, createWebHistory } from "vue-router";
import Home from "../components/Home.vue";
import Register from "../components/registerUser.vue";
import login from "../components/login.vue";

const routes = [

  {
    path: '/',
    name: 'Home',
    component: Home,
  },
{
    path: '/registerUser',
    name: 'Register',
    component: Register,
},
{
    path: '/login',
    name: 'login',
    component: login,
},

];

const router = createRouter({
  history: createWebHistory(),
  routes, // Aquí pasas el array routes directamente
  linkActiveClass: "active-link",
});

export default router;