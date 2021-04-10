<template>
    <div class="mui-content">
        <div class="mui-card">
            <ul class="mui-table-view">
                <li class="mui-table-view-cell">付款</li>
                <!--mui-icon-weixin 使用的是MUI ui 封装好的 微信的图标 ，也可以更换成其他的。  MUI 和Mint UI 不是一个东西~    -->
                <li class="mui-table-view-cell">支付方式<span class="mui-icon mui-icon-weixin spanright"></span></li>
            </ul>
            <form class="mui-input-group">
                <div class="mui-input-row">
                    <label>金额</label>
                    <!-- v-model和订单金额进行绑定 readOnLy只读，不能修改-->
                    <input type="text" v-model="orderAmount" readonly/>
                </div>
                <button type="button" class="mui-btn mui-btn-block mui-btn-success" @click="pay()">付款</button>
            </form>
        </div>
    </div>
</template>

<script>
    import { Toast } from 'mint-ui';
    export default {
        name: "Wallet",
        data(){
            return{
                orderAmount: null
            }
        },
        created(){
            this.orderAmount = this.$route.query.orderAmount
        },
        methods:{
            pay(){
                //支付交由后端处理
                const _this = this
                axios.put('http://localhost:8180/order-service/buyer/order/pay/'+JSON.parse(window.localStorage.getItem('access-user')).openid+'/'+this.$route.query.orderId).then(function (resp) {
                    //支付完成后弹窗提示
                    let instance = Toast('支付成功');
                    setTimeout(() => {
                        instance.close();
                        _this.$router.push('/orderDetail?orderId='+_this.$route.query.orderId)
                    }, 500);
                })
            }
        }
    }
</script>

<style scoped>
    .spanright{
        display: inline-block;
        float: right;
    }
</style>
