import { createStore } from "vuex";
import VuexPersist from "vuex-persist";

const vuexPersist = new VuexPersist({
  key: "my-app",
  storage: window.localStorage,
});

export default createStore({
  state: {
    user: null,
    login: false,
  },
  mutations: {
    setUser(state, user) {
      state.user = user;
    },
    clearUser(state) {
      state.user = null;
    },
    login(state) {
      state.login = true;
    },
    logout(state) {
      state.login = false;
    },
  },
  actions: {
    setUser({ commit }, user) {
      commit("setUser", user);
    },
    clearUser({ commit }) {
      commit("clearUser");
    },
    login({ commit }) {
      commit("login");
    },
    logout({ commit }) {
      commit("logout");
    },
  },
  getters: {
    getUser: (state) => state.user,
    getLogin: (state) => state.login,
  },
  plugins: [vuexPersist.plugin],
});
