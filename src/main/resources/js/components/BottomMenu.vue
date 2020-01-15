<template>
    <v-bottom-navigation v-model="activeBtn" color="indigo">
        <v-btn>
            <span>Добавить лекарства</span>
        </v-btn>
        <v-btn>
            <span>Список заказов</span>
        </v-btn>
    </v-bottom-navigation>
</template>

<script>
    export default {
        name: "BottomMenu",
        data () {
            return {
                activeBtn: 0
            }
        },
        watch: {
            activeBtn(value) {
                this.$emit('changeBottomMenu', value)
            }
        }
    }
</script>

<style scoped>

</style>