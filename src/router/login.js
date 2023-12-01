// import Vue from 'vue'
// import Vuex from 'vuex'

// Vue.use(Vuex)

// export default new Vuex.Store({
//   state: {
//     id: '',
//     // token: '',
//   },
  
//   getters: {
//     isUserLogin(state) {
//       return state.id !== '';
//     },
//   },

//   mutations: {
//     //해당 state를 사용하기 위해 아래 값 입력 (state)
//     setId(state, id) {
//       state.id = id;
//     },
//     // setToken(state, token) {
//     //   state.token = token;
//     // },
//     logOut(state) {
//       state.id = '';
//       alert("로그아웃 되었습니다.");
//     },
//   },
//   actions: {
//   },
//   modules: {
//   }
// })
// //메소드는 mutations와 actions로 주로 들어가는데 일반적인 로직은 acrions로 들어간다.
// // mutations : 비동기 처리x, 데이터변경o
// // actions : 비동기 처리o, 데이터변경x
// //data부분 공통으로 사용할때는 state로 입력 해주고, tihs.id가 아닌 state.id로 입력해준다.
// // commit() - mutations를 실행햐겠다.
// // commit('assignDb') mutations에 있는 assing 을 실행하겠다