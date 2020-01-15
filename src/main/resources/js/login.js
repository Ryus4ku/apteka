import Vue from 'vue'
import 'api/resource'
import router from 'router/router'
import '@babel/polyfill'
import store from '/store/mainStore'
import VueResource from 'vue-resource'
import Log from 'pages/Auth/Log.vue'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'

Vue.use(Vuetify);

Vue.use(VueResource);

new Vue({
    el: '#login',
    router,
    store,
    render: a => a(Log)
});