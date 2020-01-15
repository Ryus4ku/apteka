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
                           :key="item.id"
                           cols="4"
                           sm="5"
                           md="4"
                           lg="3">
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
                            </v-list>
                            <v-divider/>
                            <v-card-actions>
                                <v-spacer/>
                                <v-btn v-if="!containBasket(item)" text color="success" @click="updateBaskets({value:item})">В корзину</v-btn>
                                <v-btn v-else text color="error" @click="updateBaskets({value:item, action:'remove'})">Убрать из корзины</v-btn>
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
                        Page {{ page }} of {{ numberOfPages }}
                    </span>
                    <v-btn fab dark
                           color="blue darken-3"
                           class="mr-1"
                           @click="formerPage">
                        <
                    </v-btn>
                    <v-btn fab dark
                           color="blue darken-3"
                           class="ml-1"
                           @click="nextPage">
                        >
                    </v-btn>
                </v-row>
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
                this.remedyList = await this.getRemedyList();
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