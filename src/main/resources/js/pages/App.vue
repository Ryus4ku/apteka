<template>
    <v-app>
        <v-content>
            <top-menu/>
            <v-layout fill-height style="height: calc(100% - 48px)">
                <router-view/>
            </v-layout>
        </v-content>
    </v-app>
</template>

<script>
    import {mapActions} from 'vuex'
    import TopMenu from "../components/TopMenu.vue";
    export default {
        name: "App",
        components: {TopMenu},
        methods: {
            ...mapActions(['getContext'])
        },
        mounted() {
            this.getContext()
        }
    }
</script>

<style lang="scss">
    @import '../../styles/index';
</style>