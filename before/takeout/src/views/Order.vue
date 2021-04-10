<template>
    <div class="app-container">
        <ul class="mui-table-view mui-table-view-chevron">
            <!-- v-for循环遍历data取出数据进行展示,每个订单都附上超链接，可以跳转到订单详情页面-->
            <li class="mui-table-view-cell mui-media" v-for="item in data">
                <router-link class="mui-navigate-right" :to="'/orderDetail?orderId='+item.orderId"  >
                    <div class="mui-row">
                        <div class="mui-media-body mui-col-xs-9" style="margin: 0;padding: 0">
                            <span style="float: left">订单号：{{item.orderId}}</span>
                            <span style="float: right">￥ <span class="price">{{item.orderAmount}}</span></span>
                        </div>
                        <div class="mui-media-body mui-col-xs-9" style="margin-top: 10px">
                            <span style="float: left;color: #c8cbcf;font-size: 12px;">{{dateFormat(item.createTime)}}</span>
                            <span class="orderStatus">{{transformOrderStatus(item.orderStatus)}}</span>
                        </div>
                    </div>
                </router-link>
            </li>
        </ul>
    </div>
</template>

<script>
    export default {
        name: "Order",
        data(){
            return {
                data:[
                    {
                        "orderId": "161873371171128075",
                        "buyerName": "张三",
                        "buyerPhone": "18868877111",
                        "buyerAddress": "科技路",
                        "buyerOpenid": "18eu2jwk2kse3r42e2e",
                        "orderAmount": 0,
                        "orderStatus": 0,
                        "payStatus": 0,
                        "createTime": 1490171219,
                        "updateTime": 1490171219,
                        "orderDetailList": null
                    },
                    {
                        "orderId": "161873371171128076",
                        "buyerName": "张三",
                        "buyerPhone": "18868877111",
                        "buyerAddress": "科技路",
                        "buyerOpenid": "18eu2jwk2kse3r42e2e",
                        "orderAmount": 0,
                        "orderStatus": 0,
                        "payStatus": 0,
                        "createTime": 1490171219,
                        "updateTime": 1490171219,
                        "orderDetailList": null
                    }]
            }
        },
        created(){
            this.$store.state.index = 2;
            // const _this = this
            // axios.get('http://localhost:8180/order-service/buyer/order/list/'+JSON.parse(window.localStorage.getItem('access-user')).openid+'/1/100').then(function (resp) {
            //     _this.data = resp.data.data
            // })
        },
        methods:{
            transformOrderStatus(status){
                switch (status){
                    case 0: return '新订单';break;
                    case 1: return '已完成';break;
                    case 2: return '已取消';break;
                }
            },
            dateFormat(time) {
                var date = new Date();
                date.getDate(time);
                var year=date.getFullYear();
                var month= date.getMonth()+1<10 ? "0"+(date.getMonth()+1) : date.getMonth()+1;
                var day=date.getDate()<10 ? "0"+date.getDate() : date.getDate();
                var hours=date.getHours()<10 ? "0"+date.getHours() : date.getHours();
                var minutes=date.getMinutes()<10 ? "0"+date.getMinutes() : date.getMinutes();
                var seconds=date.getSeconds()<10 ? "0"+date.getSeconds() : date.getSeconds();
                return year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
            }
        }
    }
</script>

<style scoped>
    .app-container{

    }
    .mui-media-body{
        font-size: 14px;
    }
    .mui-ellipsis{
        font-size: 12px;
    }
    .price{
        color: red;
        font-size: 18px;
        font-weight: bold;
    }
    a:after,a:link,a:active,a:visited{
        text-decoration:none;
    }
    .orderTime{
        color: #c8cbcf;
        font-size: 12px;
    }
    .head-img{
        width: 60px;
        height: 58px;
    }
    .orderStatus{
        display: inline-block;
        float: right;
        color: cornflowerblue;
    }
</style>
