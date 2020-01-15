<template>
    <div style="width: 100%">
        <v-card v-if="bottomMenuValue === 0"
                class="mx-auto mt-5" width="500" height="fit-content" align="center" justify="space-around">
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
        <v-card v-if="bottomMenuValue === 1"
                class="mx-auto" width="70%" height="100%" align="center" jusify="space-around" style="border-radius: 0">
            <v-list>
                <v-list-group v-for="item in purchases"
                              :key="item.id"
                              v-model="item.active"
                              no-action>
                    <template v-slot:activator>
                        <v-list-item-content style="text-align: left">
                            <v-list-item-title>
                                <v-row class="ma-0">
                                    <span>{{item.fio}}</span>
                                    <v-spacer/>
                                    <span style="color: limegreen">{{item.totalPrice}} рублей</span>
                                </v-row>
                            </v-list-item-title>
                            <v-list-item-subtitle v-text="item.phoneNumber" />
                        </v-list-item-content>
                    </template>

                    <v-list-item v-for="subItem in item.baskets"
                                 :key="subItem.id" @click="" class="pl-8">
                        <v-col cols="1" sm="12" md="12" lg="12">
                            <v-list-item-content>
                                <v-list-item-title v-text="subItem.name" style="text-align: left" />
                                <v-list-item-subtitle  style="text-align: right" v-text="`${parseFloat(subItem.price)} X ${subItem.purchaseCount} = ${subItem.purchaseCount * parseFloat(subItem.price)} рублей`"/>
                            </v-list-item-content>
                            <v-divider/>
                            <v-list-item-content style="text-align: left" >
                                {{item.comment}}
                            </v-list-item-content>
                            <v-list-item-action style="text-align: right; width: 100%;">
                                <v-spacer/>
                                <v-btn color="primary" @click="closePurchase(item)">Завершить заказ</v-btn>
                            </v-list-item-action>
                        </v-col>
                    </v-list-item>
                </v-list-group>
            </v-list>
        </v-card>

        <bottom-menu style="position: absolute" @changeBottomMenu="changeMenu" />
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
            ...mapActions(['saveRemedy', 'getActualPurchases', 'closePurchase']),
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
                this.remedy.count = 0;
            }
        },
        computed: {
            ...mapState(['purchases'])
        },
        mounted() {
            this.getActualPurchases();
        }
    }
</script>

<style scoped>

</style>