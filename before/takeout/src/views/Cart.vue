<template>
    <!-- view model data-->
    <!-- 分上中下三层-->
    <div class="mui-content">
        <!-- 顶部图片 -->
        <div><img :src="logo" alt="" class="logo"/></div>

        <!-- 菜单主体 分为左侧菜单和右侧的商品列表-->
        <div class="mui-row">
            <!-- 左侧菜单; mui; <a>标签+v-for循环
            menu-active是标签样式 menuIndex==index1是条件判断 成立时将当前标签更换成该样式
            @click="menuIndex = index1"表示鼠标点击标签时将该标签的下标index1赋给menuIndex变量 实现动态切换
            (item,index1)表示取data中对象的下标
             这里的item和index1是可以自定义的名字,但是要处处保持一致-->
            <div class="mui-col-xs-3" style="border-right: 1px solid #c8c7cc">
                <div id="segmentedControls" class="mui-segmented-control mui-segmented-control-inverted mui-segmented-control-vertical">
                    <a class="mui-control-item" :class="{'menu-active':menuIndex==index1}" @click="menuIndex = index1" v-for="(item,index1) in data">{{item.name}}</a>
                </div>
            </div>

            <!-- 商品列表segmentedControlContents
            v-for="(item,index) in data 第一层循环 从data中取对象下标 即早餐午餐晚餐 将其循环展示
            v-for="item1 in item.foods 第二层循环 data每个对象中的foods数组，将其循环展示 注意第二层循环用的名字是item1代表foods里的对象，与item区分开来
            最里层的div与data里的foods的属性对应
            注意img :src = item1.icon 冒号表示间接取 如果直接赋予网址则不需要冒号 img src= 具体url
            v-show="menuIndex==index" 条件成立时才显示当前data对象 实现了早餐午餐晚餐之间的切换
            @click="changeQuantity(item1,'minus')"点击调用changeQuantity方法改变数量，minus减plus加
            -->
            <div id="segmentedControlContents" class="mui-col-xs-9">
                <div v-for="(item,index) in data" v-show="menuIndex==index">
                    <div class="itembox mui-row" v-for="item1 in item.foods">
                        <div class="mui-col-xs-3">
                            <img :src="item1.icon"/>
                        </div>
                        <div class="mui-col-xs-9">
                            <div class="item">
                                <div class="itemmain">
                                    {{item1.name}}
                                </div>
                                <div class="itemsub">
                                    {{item1.description}}
                                </div>
                            </div>
                            <div class="operation">
                                <div class="operationPrice">￥11</div>
                                <div class="operationSelect">
                                    <span class="mui-icon mui-icon-minus operationMinus" @click="changeQuantity(item1,'minus')"></span>
                                    <span>{{item1.quantity}}</span>
                                    <span class="mui-icon mui-icon-plus operationPlus" @click="changeQuantity(item1,'plus')"></span>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

            </div>

        </div>

        <!-- 底部菜单 -->
        <div style="position: relative;top: 0px;" class="mui-bar mui-bar-tab cart-box" id="cart-box">
            <div class="cart-container">
                <div class="cell1">
                    <div class="cart-icon-box">
                        <span class="mui-icon mui-icon-contact">
                          <span class="mui-badge" id="goodsNum">{{totalQuantity}}</span>
                        </span>
                    </div>
                </div>
                <div class="cell12">
                    ￥<span id="totalMoney">{{totalPrice}}</span>
                </div>
                <div class="cell2" @click="clear">
                    <span class="mui-icon mui-icon-trash"></span>
                    清空餐车
                </div>
                <!-- 点击后submit-->
                <div class="cell3" @click="submit">
                    <span class="mui-icon mui-icon-checkmarkempty"></span>
                    确认下单
                </div>
            </div>
        </div>
<!--        {{this.$store.state.selectedArray}}-->
    </div>
</template>

<script>
    import { Toast } from 'mint-ui';
    export default {
        name: "Cart",
        data() {
            return {
                totalQuantity:0,
                totalPrice:0,
                menuIndex: 0,//menuIndex变量用来动态选中某个左侧菜单标签 初始值为0，进入cart页面时默认选中的是早餐
                 logo: "../static/1.jpg",//指向public/static里的静态图片资源
                //data用了双层数组嵌套 name-->foods 可以在name间切换 各个name下再展示foods
                data: [
                    {
                        "name": "早餐",
                        "type": 1,
                        "foods": [
                            {
                                "id": "1",
                                "name": "豆浆",
                                "price": 3.5,
                                "description": "很好喝",
                                "icon": "http://5b0988e595225.cdn.sohucs.com/images/20181101/225d20df2bc14cefb363686d90d47004.jpeg",
                                "quantity": 0,//顾客已选数量
                                "stock": 6//库存
                            },
                            {
                                "id": "2",
                                "name": "红烧肉2",
                                "price": 33.50,
                                "description": "很好吃2",
                                "icon": "http://n.sinaimg.cn/sinacn10/320/w640h480/20180827/5682-hifuvph4790369.jpg",
                                "quantity": 0,
                                "stock": 200
                            },
                            {
                                "id": "3",
                                "name": "红烧肉2",
                                "price": 33.50,
                                "description": "很好吃2",
                                "icon": "http://n.sinaimg.cn/sinacn10/320/w640h480/20180827/5682-hifuvph4790369.jpg",
                                "quantity": 0,
                                "stock": 200
                            }
                        ]
                    },
                    {
                        "name": "午餐",
                        "type": 2,
                        "foods": [
                            {
                                "id": "4",
                                "name": "红烧肉",
                                "price": 33.50,
                                "description": "很好吃",
                                "icon": "http://n.sinaimg.cn/sinacn10/320/w640h480/20180827/5682-hifuvph4790369.jpg",
                                "quantity": 0,
                                "stock": 200
                            },
                            {
                                "id": "5",
                                "name": "红烧肉2",
                                "price": 33.50,
                                "description": "很好吃2",
                                "icon": "http://n.sinaimg.cn/sinacn10/320/w640h480/20180827/5682-hifuvph4790369.jpg",
                                "quantity": 0,
                                "stock": 200
                            }
                        ]
                    },
                    {
                        "name": "晚餐",
                        "type": 2,
                        "foods": [
                            {
                                "id": "6",
                                "name": "炖排骨",
                                "price": 33.50,
                                "description": "很好吃！！！",
                                "icon": "http://n.sinaimg.cn/sinacn10/320/w640h480/20180827/5682-hifuvph4790369.jpg",
                                "quantity": 0,
                                "stock": 200
                            },
                            {
                                "id": "7",
                                "name": "牛肉面",
                                "price": 33.50,
                                "description": "很好吃",
                                "icon": "http://n.sinaimg.cn/sinacn10/320/w640h480/20180827/5682-hifuvph4790369.jpg",
                                "quantity": 0,
                                "stock": 200
                            },
                            {
                                "id": "3",
                                "name": "火锅",
                                "price": 33.50,
                                "description": "很好吃",
                                "icon": "http://n.sinaimg.cn/sinacn10/320/w640h480/20180827/5682-hifuvph4790369.jpg",
                                "quantity": 0,
                                "stock": 200
                            }
                        ]
                    }
                ]
            }
        },
        created(){
            this.$store.state.index = 1;
            // this.$store.state.selectedArray = [];
            // const _this = this
            // axios.get('http://localhost:8180/product-service/buyer/product/list').then(function (resp) {
            //     _this.data = resp.data.data
            // })
        },
        methods: {
            //提交 确认下单时进行判断
            submit(){
                const _this = this
                //若共享数据数组长度为0，说明没有选择商品
                if(this.$store.state.selectedArray.length == 0) {
                    let instance = Toast('请选择商品');//弹框提示
                    setTimeout(() => {//设置弹框显示时长
                        instance.close();
                    }, 3000)
                    return
                }
                this.$router.push('/info')//已选商品，能够下单，转到info页面
            },
            //清空购物车 需要清理四个地方
            clear(){
                this.totalQuantity=0;
                this.totalPrice=0;
                //菜品的数量设置为0
                for(var i = 0; i < this.data.length;i++){//第一层循环 早餐晚餐午餐
                    var item = this.data[i];
                    for(var j=0;j<item.foods.length;j++){//第二层 foods
                        item.foods[j].quantity = 0;//数量改为0
                    }
                }
                //清空共享数据数组
                this.$store.state.selectedArray=[]
            },
            changeQuantity(item,type){
                switch (type) {//switch根据type   判断加还是减
                    case "minus":
                        //若当前商品已选为0，不能--，直接return跳出，相当于操作无效
                        if(item.quantity == 0)return;
                        //不为0则做相应减法
                        item.quantity--;
                        this.totalQuantity--;
                        this.totalPrice -= item.price;
                        //修改共享数据数组
                        for(var i=0;i<this.$store.state.selectedArray.length;i++){
                            if(item.id == this.$store.state.selectedArray[i].productId){//根据id匹配
                                this.$store.state.selectedArray[i].productQuantity = item.quantity//修改数量
                                //数量为0时用splice方法移除该条数据，从i开始移除1条。否则存在id=i，数量为0的数据，是没有意义的
                                if(item.quantity==0) this.$store.state.selectedArray.splice(i,1)
                                return;//返回
                            }
                        }
                        break;
                    case "plus":
                        //点击后先对顾客已选数量++
                        item.quantity++;
                        //判断当前商品已选数量和其库存关系
                        if(item.quantity <= item.stock){
                            //若未超过库存则左下角已选所有商品商品总数++
                            this.totalQuantity++;
                            //价格+
                            this.totalPrice += item.price;
                        }else{
                            //若超过最大库存，修改顾客已选数量为最大库存
                            item.quantity = item.stock;
                            //弹窗提示
                            let instance = Toast(item.name+'已被你抢空！');
                            //setTimeout设置弹框显示时间
                            setTimeout(() => {
                                instance.close();
                            }, 3000)
                            //return跳出方法
                            return
                        }
                        //查询该对象在共享数据数组中是否已经存在，若已存在，修改数量即可
                        //这里跟hashmap判断key是否存在一个道理，若已有key，修改value，否则put新的key-value
                        for(var i=0;i<this.$store.state.selectedArray.length;i++){
                            if(item.id == this.$store.state.selectedArray[i].productId){//根据id来判断
                                this.$store.state.selectedArray[i].productQuantity = item.quantity//id相等则修改数量
                                return;//return返回 不执行下面的添加新对象的程序
                            }
                        }
                        //若不存在，则添加对象
                        //this指当前vue对象
                        // $store.state.selectedArray路径
                        // .push存入 只需存入id和数量
                        this.$store.state.selectedArray.push({
                            productId:item.id,
                            productQuantity:item.quantity
                        });
                        break;
                }
            }
        }
    }
</script>

<style scoped>
    .operation{
        width: 220px;
        height: 20px;
        margin-top: 3px;
    }
    .operationPrice{
        float: left;
        width: 50px;
        height: 100%;
        font-size: 18px;
        font-weight: bold;
        color: red;
    }
    .operationType{
        float: left;
        position: relative;
        left: 10px;
        width: 60px;
        height: 100%;
    }
    .operationSelect{
        float: left;
        width: 110px;
        height: 100%;
        position: relative;
        left: 10px;
    }
    .operationMinus{
        color: red;
        float: left;
        margin-left: 10px;
        font-weight: bold;
    }
    .operationPlus{
        color:green;
        float: right;
        margin-right: 15px;
        font-weight: bold;
    }
    .mui-row.mui-fullscreen>[class*="mui-col-"] {
        height: 100%;
    }
    .mui-col-xs-3 {
        overflow-y: auto;
        height: 100%;
    }
    .mui-segmented-control .mui-control-item {
        line-height: 50px;
        width: 100%;
    }
    .mui-segmented-control.mui-segmented-control-inverted .mui-control-item.mui-active {
        background-color: #fff;
    }

    #segmentedControlContents,#segmentedControls{
        background-color: white;
    }

    #segmentedControlContents{
        padding: 15px 0 0 10px;
    }
    .mui-content{
        padding: 0;
        background-color: white;
    }
    .itembox{
        margin: 0 0 16px;
        padding: 0 0 5px;
        border-bottom: 1px silver solid;
    }
    img{
        width: 60px;
        height: 60px;
        vertical-align: center;
    }
    .oper-icon{
        display: inline-block;
        float: right;
        padding-right: 10%;
        /*color: blue;*/
        position: relative;
        left: 20px;
        background-color: green;
        width: 60px;
    }
    .item{
        width: 250px;
        height: 40px;
        text-align: left;
    }
    .itemmain{
        font-size: 14px;
        font-weight: bold;
    }
    .itemsub{
        font-size: 13px;
        font-family: 'Helvetica Neue', Helvetica, sans-serif;
    }

    .logo{
        width: 100%;
        height: 100%;
    }
    .menu-active{
        color: red;
        background-color: #c8c7cc;
    }

    .cell1, .cell2, .cell3 {
        display: inline-block;
    }
    .cart-container{
        display: flex;
        padding: 0;
        margin: 0;
        height: 51px;
    }
    .cell1{
        flex: 2;
        background-color: #141d27;
        color: #cccccc;
    }

    .cell12{
        flex: 2;
        background-color: #141d27;
        text-align: center;
        padding-right: 0;
        color: white;
        font-size: 17px;
        line-height: 51px;
    }
    .cell2{
        flex: 3;
        background-color: #dd524d;
        text-align: center;
        padding-left: 0;
        margin: 0;
        color: white;
        font-size: 15px;
    }

    .cell3{
        flex: 3;
        background-color: #007aff;
        text-align: center;
        padding-right: 10px;
        color: white;
        font-size: 15px;
    }
    .cart-icon-box{
        border: black 5px solid;
        border-radius: 50%;
        height: 50px;
        width: 50px;
        text-align: center;
        background-color: #6c757d;
        z-index: 100;
        position: relative;
        top: -15px;
        left: 10%;
    }


</style>
