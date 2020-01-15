<template>
    <v-bottom-navigation v-model="activeBtn" color="indigo">
        <v-btn>
            <span>Добавить лекарства</span>
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
        methods: {
            change() {
                this.$emit('changeBottomMenu', this.activeBtn)
            }
        }
    }
</script>

<style scoped>

</style>