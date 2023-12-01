import Vue from 'vue'
import Vuex, { Store } from 'vuex'
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex)
export default new Vuex.Store({
  state: {
    id: '',
    bno:'',
  },
  
  getters: {
    // this.$store.getters.isUserLogin;
    isUserLogin(state) {
      return state.id !== '';
    },
    bno(state){
      console.log("getters>>state.bno>>", state.bno);
      return state.bno;
    },
    local(state){
      console.log("getters.local>>>>>>",state.id);
      localStorage.setItem("user",state.id);  //저장
      // localStorage.getItem(state.id) //조회
      // localStorage.removeItem(키) // remove
      // localStorage.clear() // clear
    },localClear(){
      localStorage.clear();
    },
  },

  mutations: {
    //해당 state를 사용하기 위해 아래 값 입력 (state), 로그인아이디
    setId(state, id) {
      state.id = id;
    },
    logOut(state) {
      state.id = '';
      alert("로그아웃 되었습니다.");
    },
    //글번호
    getBno(state, bno){
      state.bno = bno
      console.log("mutations>>state.bno>>",state.bno);
    }
    
  },
  actions: {
  },
  modules: {
  },
  // localStorage 저장
  plugins: [
    createPersistedState({
      // paths: ['module'],
    })
  ]
})





//메소드는 mutations와 actions로 주로 들어가는데 일반적인 로직은 acrions로 들어간다.
// mutations : 비동기 처리x, 데이터변경o
// actions : 비동기 처리o, 데이터변경x
//data부분 공통으로 사용할때는 state로 입력 해주고, tihs.id가 아닌 state.id로 입력해준다.
// commit() - mutations를 실행햐겠다.
// commit('assignDb') mutations에 있는 assing 을 실행하겠다