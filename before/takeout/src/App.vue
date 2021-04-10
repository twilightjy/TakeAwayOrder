<template>
  <div id="app">
      <!-- 顶部导航 -->
      <mt-header title="外卖订餐系统">
          <mt-button icon="back" slot="left" @click="back">返回</mt-button>
      </mt-header>

      <!--子页面-->
    <router-view/>

      <!--底部导航 在任何页面都显示 图标采用的MUI的组件 三个div 每个div里面2个span，一个展示图片一个展示文字
      store.state.index点击时修改该全局共享数据的值，用来选中时更改标签样式-->
      <nav class="mui-bar mui-bar-tab" id="bar-tab">
          <div class="mui-tab-item" @click="$store.state.index=1;$router.push('/cart')">
              <span class="mui-icon mui-icon-compose" :class="{'mui-active':$store.state.index==1}"></span>
              <span class="mui-tab-label" :class="{'mui-text-active':$store.state.index==1}">点餐</span>
          </div>
          <div class="mui-tab-item"  @click="$store.state.index=2;$router.push('/order')">
              <span class="mui-icon mui-icon-bars" :class="{'mui-active':$store.state.index==2}"></span>
              <span class="mui-tab-label" :class="{'mui-text-active':$store.state.index==2}">订单</span>
          </div>
          <div class="mui-tab-item" @click="$store.state.index=3;$router.push('/mine')">
              <span class="mui-icon mui-icon-person" :class="{'mui-active':$store.state.index==3}"></span><span class="mui-badge mui-badge-danger">45</span>
              <span class="mui-tab-label" :class="{'mui-text-active':$store.state.index==3}">我的</span>
          </div>
      </nav>

  </div>
</template>

<style>
  #app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }

  #nav {
    padding: 30px;
  }

  #nav a {
    font-weight: bold;
    color: #2c3e50;
  }

  #nav a.router-link-exact-active {
    color: #42b983;
  }
</style>

<script>
  export default {
    methods: {
      test:function () {
        axios.get('http://localhost:9090/student/list').then(function (response) {
          console.log(response.data)
        })
      },
        //history 返回上一个页面
        back() {
            history.go(-1)
        }

    }
  }
</script>

