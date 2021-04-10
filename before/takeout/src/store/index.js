import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  //共享数据,不同页面之间都能访问state中的数据 ，这里使用的h5 local storage 数据共享功能
  state: {
    selectedArray:[],
    index:0
  },
  mutations: {
  },
  actions: {
  },
  modules: {
  }
})
