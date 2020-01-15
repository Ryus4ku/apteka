<template>
    <v-card class="mx-auto" width="70%" height="100%" align="center" jusify="space-around" style="border-radius: 0">
        <v-card-title>Каталог лекарств</v-card-title>
        <v-data-iterator :items="remedyList"
                         :items-per-page.sync="itemsPerPage"
                         :page="page"
                         item-key="name"
                         hide-default-footer class="ml-2 mr-2">
            <template v-slot:default="{ items }">
                <v-row>
                    <v-col v-for="item in items"
                           :key="item.id" cols="4" sm="5" md="4" lg="3">
                        <v-card>
                            <v-card-title>
                                <h4>{{ item.name }}</h4>
                            </v-card-title>
                            <v-divider/>
                            <v-list dense>
                                <v-list-item>
                                    <v-list-item-content>Цена:</v-list-item-content>
                                    <v-list-item-content v-if="item.price" class="align-end">{{ item.price }}</v-list-item-content>
                                    <v-list-item-content v-else class="align-end" style="color: red">Цена не указана</v-list-item-content>
                                </v-list-item>
                                <v-list-item>
                                    <v-list-item-content>Кол-во:</v-list-item-content>
                                    <v-list-item-content class="align-end"
                                                         :style="{color: item.count > 0 ? 'blue' : 'red'}">
                                        {{item.count}}
                                    </v-list-item-content>
                                </v-list-item>
                            </v-list>
                            <v-divider/>
                            <v-card-actions>
                                <v-text-field v-model="item.purchaseCount" style="width: 20px">
                                    <v-icon slot="prepend" color="red"
                                            :disabled="item.purchaseCount === 0"
                                            @click="remove(item)">remove</v-icon>
                                    <v-icon slot="append" color="green"
                                            :disabled="item.count === 0"
                                            @click="add(item)">add</v-icon>
                                </v-text-field>
                                <v-spacer/>
                                <v-btn v-if="!containBasket(item)" text color="success" @click="updateBaskets({value:item})">В корзину</v-btn>
                                <v-btn v-else text color="error" @click="updateBaskets({value:item, action:'remove'})">Убрать</v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-col>
                </v-row>
            </template>
            <template v-slot:footer>
                <v-row class="mt-2 ma-0 bottom-toolbar" align="center" justify="center" style="position: absolute; bottom: 1%; width: 98%">
                    <span class="grey--text">Кол-во элементов на странице</span>
                    <v-menu offset-y>
                        <template v-slot:activator="{ on }">
                            <v-btn dark text color="primary" class="ml-2" v-on="on">
                                {{ itemsPerPage }}
                                <v-icon>mdi-chevron-down</v-icon>
                            </v-btn>
                        </template>
                        <v-list>
                            <v-list-item v-for="(number, index) in itemsPerPageArray"
                                         :key="index" @click="updateItemsPerPage(number)">
                                <v-list-item-title>{{ number }}</v-list-item-title>
                            </v-list-item>
                        </v-list>
                    </v-menu>

                    <v-spacer/>

                    <span class="mr-4 grey--text">
                        Страница {{ page }} из {{ numberOfPages }}
                    </span>
                    <v-btn fab dark
                           color="blue darken-3"
                           class="mr-1"
                           @click="formerPage">
                        <v-icon>keyboard_arrow_left</v-icon>
                    </v-btn>
                    <v-btn fab dark
                           color="blue darken-3"
                           class="ml-1"
                           @click="nextPage">
                        <v-icon>keyboard_arrow_right</v-icon>
                    </v-btn>
                </v-row>
            </template>
            <template v-slot:no-data>
                <v-row class="ma-0 align-center justify-center">Каталог пуст</v-row>
            </template>
        </v-data-iterator>
    </v-card>
</template>

<script>
    import {mapActions, mapMutations, mapState} from 'vuex'
    export default {
        name: "Catalog",
        data() {
            return {
                itemsPerPageArray: [4, 8, 12],
                itemsPerPage: 4,
                remedyList: [],
                page: 1,
            }
        },
        methods: {
            ...mapActions(['getRemedyList']),
            ...mapMutations(['updateBaskets']),
            async getList() {
                const result = await this.getRemedyList();
                this.remedyList = result.map(item => {
                    item.purchaseCount = 0;
                    return item;
                });

                this.baskets.forEach(basket => {
                    const finded = this.remedyList.find(remedy => remedy.id === basket.id);
                    if (finded) {
                        finded.purchaseCount = basket.purchaseCount;
                        finded.count = basket.count;
                    }
                })
            },
            nextPage () {
                if (this.page + 1 <= this.numberOfPages) this.page += 1
            },
            formerPage () {
                if (this.page - 1 >= 1) this.page -= 1
            },
            updateItemsPerPage (number) {
                this.itemsPerPage = number
            },
            containBasket(item) {
                return !!this.baskets.find(({id}) => id === item.id)
            },
            remove(item) {
                --item.purchaseCount;
                ++item.count;
            },
            add(item) {
                ++item.purchaseCount;
                --item.count;
            }
        },
        computed: {
            ...mapState(['baskets']),
            numberOfPages() {
                return Math.ceil(this.remedyList.length / this.itemsPerPage)
            },
        },
        mounted() {
            this.getList()
        }
    }
</script>

<style scoped>

</style>