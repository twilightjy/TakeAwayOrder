<template>
  <div class="page-wrap">
    <h1>外卖订餐系统 V1.0</h1>
    <h2>技术架构 Spring Cloud Alibaba + Vue</h2>
    <h3>开发者:爪哇队</h3>
  </div>
</template>

<script>
  export default {
    created() {
      this.$store.state.index = 3
    }
  }
</script>

<style>
  .page-wrap {
    overflow: auto;
    height: 100%;
    margin-top: 60px;
  }
</style>