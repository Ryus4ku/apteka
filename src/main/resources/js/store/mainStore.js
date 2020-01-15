import Vue from 'vue'
import Vuex from 'vuex'

import to from '../functionLib/to.js'

Vue.use(Vuex);

const store = new Vuex.Store({
    modules: {},
    state: {
        context: {
            admin: false,
            authorized: false
        },
        baskets: [],
        purchases: []
    },
    getters: {},
    mutations: {
        updateAuthStatus(state, value) {
            const {authorized, admin} = value;
            state.context.authorized = authorized;
            state.context.admin = admin;
        },
        updateBaskets(state, {value, action = 'add'}) {
            if (action === 'add') {
                state.baskets.push(value)
            } else {
                const index = state.baskets.findIndex(({id}) => id === value.id);
                state.baskets.splice(index, 1);
            }
        },
        updatePurchases(state) {
            state.purchases.forEach(purchase => purchase.active = false)
        },
        removePurchase(state, item) {
            const index = state.purchases.findIndex(purchase => purchase.id === item.id);
            state.purchases.splice(index, 1);
        }
    },
    actions: {
        async login({commit, state}, user) {
            const {err, res} = await to(Vue.http.post('/login', user));
            if (!err) {
                setTimeout(((ok) => state.isLogin = ok)(res.ok), 3000);
                Vue.http.get('/')
            } else {
                const isStatus404 = err.status === 404;
                state.isLogin = isStatus404 ? true : err.ok;
            }
        },
        async register({}, user) {
            await to(Vue.http.post('/register', user));
        },
        async getContext({commit}) {
            const {err, res} = await to(Vue.http.get('/getContext'));
            if (!err) {
                commit('updateAuthStatus', await res.json())
            }
        },
        logout({}) {
            Vue.http.get('/logout')
        },
        async saveRemedy({}, remedy) {
            await to(Vue.http.post('/remedy/new', remedy));
        },
        async getRemedyList() {
            const {err, res} = await to(Vue.http.get('/remedy/list'));
            if (!err) {
                return await res.json();
            }
        },
        async initPurchase({}, purchase) {
            await to(Vue.http.post('/basket/new', purchase));
        },
        async getActualPurchases({commit, state}) {
            const {err, res} = await to(Vue.http.get('/purchase/list'));
            if (!err) {
                state.purchases = await res.json();
                commit('updatePurchases');
                return true;
            }
        },
        async closePurchase({commit}, item) {
            await to(Vue.http.get(`/purchase/close/${item.id}`));
            commit('removePurchase', item);
        }
    }
});

export default store;