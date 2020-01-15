<template>
    <v-container fluid fill-height id="log" style="top: -56px">
        <v-layout align-center justify-center>
            <v-flex xs9 sm7 md5 lg3>
                <v-card class="elevation-12">
                    <v-toolbar dark>
                        <v-toolbar-title>Авторизация</v-toolbar-title>
                    </v-toolbar>
                    <v-flex xs10 offset-xs1 pb-2 pt-3>
                        <form id="loginForm">
                            <v-text-field required
                                          v-model="user.username"
                                          ref="username"
                                          name="username"
                                          label="username">
                            </v-text-field>
                            <v-text-field v-model="user.password"
                                          :append-icon="showPass ? 'visibility' : 'visibility_off'"
                                          :type="showPass ? 'text' : 'password'"
                                          @click:append="showPass = !showPass"
                                          ref="password"
                                          name="password"
                                          label="Пароль"
                                          required>
                            </v-text-field>
                            <v-layout row justify-space-between>
                                <v-btn tile @click="register(user)" color="primary">Регистрация</v-btn>
                                <v-spacer/>
                                <v-btn tile @click="submit" color="primary">Вход</v-btn>
                            </v-layout>
                        </form>
                    </v-flex>
                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import {mapActions, mapState} from 'vuex'

    export default {
        name: "Log",
        data: function() {
            return {
                user: {
                    username: '',
                    password: ''
                },
                showPass: false,
                showRepeatPass: false,
                isUsernameChecked: false,
                isEmptyPass: false,
                showValidMessage: false,
                validCreatePassForm: true
            }
        },
        methods: {
            ...mapActions(['login','register']),
             submit: async function() {
                if (this.user.username !== '' && this.user.password !== '') {
                    let form = document.getElementById('loginForm');
                    const formData = new FormData(form);
                    console.log([form, formData]);
                    await this.login(formData);
                    await this.$router.push("/");
                    this.$router.go(0)
                }
            }
        },
        computed: {
            ...mapState(['isLogin']),
        },
        watch: {
            isLogin(success) {
                if (success) {
                    this.$router.push("/");
                    this.$router.go(0)
                }
            }
        }
    }
</script>

<style scoped>

</style>