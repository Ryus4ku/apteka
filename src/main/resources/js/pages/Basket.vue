<template>
    <v-layout>
        <v-card class="mx-auto" width="70%" height="100%" style="border-radius: 0">
            <v-card-title>Корзина</v-card-title>
            <v-divider/>
            <v-list>
                <v-list-item-group color="primary">
                    <v-list-item v-for="(item, i) in baskets" :key="i">
                        <v-list-item-content>
                            <v-list-item-title v-html="item.name"/>
                            <v-list-item-subtitle v-html="`${item.price} рублей`"/>
                        </v-list-item-content>
                        <v-list-item-action style="width: 200px">
                            <v-row>
                                <v-text-field v-model="item.purchaseCount" style="width: 20px">
                                    <v-icon slot="prepend" color="red"
                                            :disabled="item.purchaseCount === 0"
                                            @click="remove(item)">remove</v-icon>
                                    <v-icon slot="append" color="green"
                                            :disabled="item.count === 0"
                                            @click="add(item)">add</v-icon>
                                </v-text-field>
                                <v-btn text color="error" @click="updateBaskets({value:item, action:'remove'})">Удалить</v-btn>
                            </v-row>
                        </v-list-item-action>
                    </v-list-item>
                </v-list-item-group>
            </v-list>
            <v-divider style="position:absolute; bottom: 56px; width: 100%"/>
            <v-card-actions style="position: absolute; bottom: 0; width: 100%">
                <p class="ma-0">{{sum}} рублей</p>
                <v-spacer/>
                <v-dialog v-model="dialog" fullscreen hide-overlay transition="dialog-bottom-transition">
                    <template v-slot:activator="{ on }">
                        <v-btn text color="success" v-on="on">Оформить заказ</v-btn>
                    </template>
                    <v-card>
                        <v-toolbar dark color="primary">
                            <v-btn icon dark @click="dialog = false">
                                <v-icon>close</v-icon>
                            </v-btn>
                            <v-toolbar-title>Оформление заказа</v-toolbar-title>
                            <v-spacer/>
                            <v-toolbar-items>
                                <v-btn dark text @click="goPurchase">Оформить</v-btn>
                            </v-toolbar-items>
                        </v-toolbar>
                        <v-list three-line subheader>
                            <v-subheader>Данные покупателя</v-subheader>
                            <v-list-item>
                                <v-list-item-content>
                                    <v-list-item-title>Ф.И.О.</v-list-item-title>
                                    <v-text-field style="max-width: 20%" class="pa-0 ma-0" v-model="fio" />
                                </v-list-item-content>
                            </v-list-item>
                            <v-list-item>
                                <v-list-item-content>
                                    <v-list-item-title>Номер телефона</v-list-item-title>
                                    <v-text-field style="max-width: 20%" class="pa-0 ma-0" v-model="phoneNumber" />
                                </v-list-item-content>
                            </v-list-item>
                        </v-list>
                        <v-divider/>
                        <v-list three-line subheader>
                            <v-subheader>Информация о заказе</v-subheader>
                            <v-simple-table>
                                <template v-slot:default>
                                    <thead>
                                    <tr>
                                        <th class="text-left">Наименование товара</th>
                                        <th class="text-left">Кол-во</th>
                                        <th class="text-left">Сумма</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr v-for="basket in baskets" :key="basket.id">
                                        <td>{{ basket.name }}</td>
                                        <td>{{ basket.purchaseCount }} X {{ basket.price }}</td>
                                        <td>{{ basket.purchaseCount * parseFloat(basket.price) }}</td>
                                    </tr>
                                    <tr>
                                        <td/>
                                        <td/>
                                        <td>{{sum}}</td>
                                    </tr>
                                    </tbody>
                                </template>
                            </v-simple-table>
                        </v-list>
                        <v-divider/>
                        <v-list three-line subheader>
                            <v-textarea filled class="mx-2 mt-3" label="Комментарий к заказу" prepend-icon="comment" v-model="comment"/>
                        </v-list>
                    </v-card>
                </v-dialog>
            </v-card-actions>
        </v-card>
    </v-layout>
</template>

<script>
    import {mapActions, mapMutations, mapState} from 'vuex'
    export default {
        name: "Basket",
        data() {
            return {
                dialog: false,
                fio: '',
                phoneNumber: '',
                comment: ''
            }
        },
        methods: {
            ...mapActions(['getRemedyList', 'initPurchase']),
            ...mapMutations(['updateBaskets']),
            remove(item) {
                --item.purchaseCount;
                ++item.count;
            },
            add(item) {
                ++item.purchaseCount;
                --item.count;
            },
            async goPurchase() {
                const {fio, phoneNumber, comment, baskets} = this;
                const purchase = {fio, phoneNumber, comment, baskets};
                await this.initPurchase(purchase);
                this.clearData()
            },
            clearData() {
                this.fio = '';
                this.phoneNumber = '';
                this.comment = '';
                this.baskets = [];
            }
        },
        computed: {
            ...mapState(['baskets']),
            sum() {
                return this.baskets && this.baskets.length
                    ? this.baskets.map(({price,purchaseCount}) => parseFloat(price)*purchaseCount).reduce((a,b) => a + b)
                    : 0
            }
        }
    }
</script>

<style scoped>

</style>