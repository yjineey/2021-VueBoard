/*
 =========================================================
 * Vue Black Dashboard - v1.1.0
 =========================================================

 * Product Page: https://www.creative-tim.com/product/black-dashboard
 * Copyright 2018 Creative Tim (http://www.creative-tim.com)

 =========================================================

 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

 */
import Vue from "vue";
import VueRouter from "vue-router";
import RouterPrefetch from 'vue-router-prefetch'
import App from "./App";
// TIP: change to import router from "./router/starterRouter"; to start with a clean layout
import router from "./router/index";
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios'
import store from './store'

import BlackDashboard from "./plugins/blackDashboard";
import i18n from "./i18n"
import './registerServiceWorker'
import locale from 'element-ui/lib/locale/lang/en'

Vue.use(ElementUI, { locale })
Vue.use(BlackDashboard);
Vue.use(VueRouter);
Vue.use(RouterPrefetch);
Vue.prototype.$axios = axios;
Vue.use(ElementUI);
Vue.config.productionTip = false;

/* eslint-disable no-new */
new Vue({
  router,
  store,
  i18n,
  // created(){
  //   const userString =localStorage.getItem("user")
  //     if(userString){
  //       const userData = JSON.parse(userString)
  //       this.$store.commit('setId', userData)

  //       axios.interceptors.response.use( 
  //       response => response, // 정상이면 response를 반환 
  //       error => { 
  //         if (error.response.status === 401) {   // 401 에러가 발생할 경우 
  //           this.$store.commit('logOut') // 강제로 로그아웃
  //           } return Promise.reject(error)  // 에러 이유를 리턴 시켜준다.
  //         }
  //       ) 
  //     }
  //   },
  render: h => h(App)
}).$mount("#app");
