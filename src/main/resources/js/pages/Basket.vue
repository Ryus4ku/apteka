<template>
    <v-layout>
        <v-card class="mx-auto" width="70%" height="100%" style="border-radius: 0">
            <v-card-title>Корзина</v-card-title>
            <v-list>
                <v-list-item-group color="primary">
                    <v-list-item v-for="(item, i) in baskets" :key="i">
                        <v-list-item-content>
                            <v-list-item-title v-html="item.name"/>
                            <v-list-item-subtitle v-html="`${item.price} рублей`"/>
                        </v-list-item-content>
                        <v-list-item-action>
                            <v-btn text color="error" @click="updateBaskets({value:item, action:'remove'})">Удалить</v-btn>
                        </v-list-item-action>
                    </v-list-item>
                </v-list-item-group>
            </v-list>
            <v-card-actions style="position: absolute; bottom: 0; width: 100%">
                <p>{{sum}} рублей</p>
                <v-spacer/>
                <v-btn text color="success">Купить</v-btn>
            </v-card-actions>
        </v-card>
    </v-layout>
</template>

<script>
    import {mapActions, mapMutations, mapState} from 'vuex'
    export default {
        name: "Basket",
        methods: {
            ...mapActions(['getRemedyList']),
            ...mapMutations(['updateBaskets']),
        },
        computed: {
            ...mapState(['baskets']),
            sum() {
                return this.baskets && this.baskets.length
                    ? this.baskets.map(({price}) => parseFloat(price)).reduce((a,b) => a + b)
                    : 0
            }
        }
    }
</script>

<style scoped>

</style>