import Vue from 'vue'
import VueRouter from 'vue-router'
import Log from "../pages/auth/Log.vue";
import Catalog from "../pages/Catalog.vue";
import Admin from "../pages/Admin.vue";
import Basket from "../pages/Basket.vue";
import Home from "../pages/Home.vue";

Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        name: 'home',
        component: Home
    },
    {
        path: '/login',
        name: 'login',
        component: Log
    },
    {
        path: '/catalog',
        name: 'catalog',
        component: Catalog
    },
    {
        path: '/admin',
        name: 'admin',
        component: Admin
    },
    {
        path: '/basket',
        name: 'basket',
        component: Basket
    }
];

const router = new VueRouter({
    mode: "history",
    hash: false,
    routes
});

export default router;