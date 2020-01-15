<template>
    <div style="width: 100%">
        <v-card class="mx-auto mt-5" width="500" height="fit-content" align="center" justify="space-around">
            <v-card-title>Новый товар</v-card-title>
            <v-card-text>
                <v-text-field v-model="remedy.name" label="Наименование"/>
                <v-text-field v-model="remedy.price"  label="Цена"/>
                <v-text-field v-model="remedy.count" label="Количество">
                    <v-icon slot="prepend" color="red"
                            :disabled="remedy.count === 0"
                            @click="remedy.count--">remove</v-icon>
                    <v-icon slot="append" color="green"
                            @click="remedy.count++">add</v-icon>
                </v-text-field>
            </v-card-text>
            <v-card-actions>
                <v-spacer/>
                <v-btn text color="deep-purple accent-4" @click="save">Добавить</v-btn>
            </v-card-actions>
        </v-card>

        <bottom-menu style="position: absolute" @changeBottomMenu="changeMenu"/>
    </div>
</template>

<script>
    import {mapActions, mapState} from 'vuex'
    import BottomMenu from "../components/BottomMenu.vue";
    export default {
        name: "Admin",
        components: {BottomMenu},
        data() {
            return {
                bottomMenuValue: 0,
                remedy: {
                    name: '',
                    price: '',
                    count: 0
                }
            }
        },
        methods: {
            ...mapActions(['saveRemedy']),
            changeMenu(value) {
                this.bottomMenuValue = value;
            },
            save() {
                this.saveRemedy(this.remedy);
                this.clear()
            },
            clear() {
                this.remedy.name = '';
                this.remedy.price = '';
            }
        }
    }
</script>

<style scoped>

</style>