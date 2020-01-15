<template>
    <div>
        <v-card color="grey lighten-4"
                flat
                height="fit-content"
                tile dark>
            <v-toolbar dense>
                <v-toolbar-title>Аптека</v-toolbar-title>
                <v-spacer/>
                <v-btn text large
                       @click="changeRoute('/')">Главная</v-btn>
                <v-btn text large
                       @click="changeRoute('/catalog')">Каталог</v-btn>

                <v-btn v-if="context.authorized" depressed text large @click="changeRoute('/basket')">Корзина ({{baskets.length}})</v-btn>


                <v-btn v-if="context.admin"
                       text large
                       @click="changeRoute('/admin')">Админ панель</v-btn>
                <v-btn v-if="!context.authorized" text large @click="changeRoute('/login')">Войти</v-btn>
                <v-btn v-else text large @click="logout()"><a href="/logout" style="text-decoration: none; color: white">Выйти</a></v-btn>
            </v-toolbar>
        </v-card>
    </div>
</template>

<script>
    import {mapActions, mapState} from 'vuex'
    export default {
        name: "TopMenu",
        data() {
            return {

            }
        },
        computed: {
            ...mapState(['context', 'baskets']),
        },
        methods: {
            ...mapActions(['logout']),
            changeRoute(address) {
                this.$router.push(address)
            },
            logout() {
                this.$router.push('/logout');
            }
        }
    }
</script>

<style scoped>

</style>