<template>
  <el-container class="home_container">
    <!--基于ElementUi的后端管理页面 el-container主体容器 el-header顶部导航条 -->
    <el-header>
      <div class="home_title">外卖订餐系统管理平台</div>
      <div class="home_userinfoContainer">
        <el-dropdown @command="handleCommand">
                  <span class="el-dropdown-link home_userinfo">
                    {{user.name}}<i class="el-icon-arrow-down el-icon--right home_userinfo"></i>
                  </span>
          <el-dropdown-menu slot="dropdown" v-if="!user">
            <el-dropdown-item command="sysMsg">系统消息</el-dropdown-item>
            <el-dropdown-item command="MyArticle">我的文章</el-dropdown-item>
            <el-dropdown-item command="MyHome">个人主页</el-dropdown-item>
            <el-dropdown-item command="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </el-header>
    <!-- 底部主体容器 aside左侧导航菜单 main中间展示页面 无右侧
    el-submenu 一级菜单 le-menu-item 二级菜单-->
    <el-container>
      <el-aside width="200px">

        <el-menu router>
          <el-submenu v-for="(item,index) in $router.options.routes" v-if="item.show" :index="index+''">
            <template slot="title">{{item.name}}</template>
            <el-menu-item v-for="(item2,index2) in item.children" v-if="item2.show" :index="item2.path"
                          :class="$route.path==item2.path?'is-active':''">{{item2.name}}</el-menu-item>
          </el-submenu>
        </el-menu>

      </el-aside>

      <el-container>
        <el-main>
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-text="this.$router.currentRoute.name"></el-breadcrumb-item>
          </el-breadcrumb>

          <router-view></router-view>

        </el-main>
      </el-container>

    </el-container>

  </el-container>
</template>
<script>
  export default{
    methods: {
      handleCommand(command){
        var _this = this;
        if (command == 'logout') {
          this.$confirm('注销登录吗?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(function () {
            localStorage.removeItem('user');
            _this.$router.go('/');
          })
        }
      }
    },
    mounted: function () {
      let user = JSON.parse(window.localStorage.getItem('user'))
      if(user == null){
        this.user = {
          name:'游客'
        }
      }else{
        this.user = user
      }

    },
    data(){
      return {
        user: null
      }
    }
  }
</script>
<style>
  .home_container {
    height: 100%;
    position: absolute;
    top: 0px;
    left: 0px;
    width: 100%;
  }

  .el-header {
    background-color: #20a0ff;
    color: #333;
    text-align: center;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .el-aside {
    background-color: #ECECEC;
  }

  .el-main {
    background-color: #fff;
    color: #000;
    text-align: center;
  }

  .home_title {
    color: #fff;
    font-size: 22px;
    display: inline;
  }

  .home_userinfo {
    color: #fff;
    cursor: pointer;
  }

  .home_userinfoContainer {
    display: inline;
    margin-right: 20px;
  }
</style>
