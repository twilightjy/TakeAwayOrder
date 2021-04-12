<template>
  <div id="app">
    <router-view/>
    <audio hidden id="notice" :src="music"></audio>
  </div>
</template>

<script>
  import HelloWorld from './components/HelloWorld.vue'

  export default {
    name: 'app',
    data() {
      return {
        websock: null,
        music:'alert.mp3',
      }
    },

    methods:{
      initWebSocket(){
        this.websock = new WebSocket('ws://'+this.$store.state.host+':8181/webSocket');
        this.websock.onmessage = this.webSocketOnMessage;
        this.websock.onopen = this.webSocketOnOpen;
        this.websock.onerror = this.websocketonerror;
        this.websock.onclose = this.webSocketClose;
      },
      webSocketOnOpen(event){
        console.log('建立连接')
      },
      webSocketOnMessage(event){
        document.getElementById('notice').play();
        const _this = this
        this.$alert('有新的订单', '消息', {
          confirmButtonText: '确定',
          callback: action => {
            _this.$router.push('/orderManage')
          }
        });
      },
      webSocketClose(event){
        console.log('连接关闭');
      }
    },
    created() {
      this.initWebSocket();
    },
    destroyed() {
      this.websock.close()
    },
    components: {
      HelloWorld
    }
  }
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }
</style>
