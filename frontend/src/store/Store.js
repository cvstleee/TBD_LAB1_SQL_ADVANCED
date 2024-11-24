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
    setOrderId(state, orderId) {
      state.orderId = orderId;
    },
    setUserId(state, userId) {
      state.userId = userId;
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
    setOrderId({ commit }, orderId) {
      commit("setOrderId", orderId);
    },
    setUserId({ commit }, userId) {
      commit("setUserId", userId);
  },
  },
  getters: {
    getUser: (state) => state.user,
    getLogin: (state) => state.login,
    getOrderId: (state) => state.orderId,
    getUserId: (state) => state.userId,
  },
  plugins: [vuexPersist.plugin],
});
