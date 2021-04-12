<template>
    <div class="mui-content">
        <form class="mui-input-group">
            <!--v-model将视图view和数据model绑定，而且是双向绑定的，一般的数据改变会使视图改变，vue实现双向绑定，视图改变也会使得数据跟着改变。
             好处就是不再需要手动的再去取数据，一步操作直接同步数据更新。 称为mvvm模型-->
            <div class="mui-input-row">
                <label>姓名</label>
                <input v-model="name" type="text" class="mui-input-clear mui-input" placeholder="请输入收件人姓名">
            </div>
<!-- 视图和数据同步更新 {{name}}-->
            <div class="mui-input-row">
                <label>电话</label>
                <input v-model="phone" type="text" class="mui-input-clear mui-input" placeholder="请输入联系电话">
            </div>
            <div class="mui-input-row">
                <label>地址</label>
                <input v-model="address" type="text" class="mui-input-clear mui-input" placeholder="请输入送货地址">
            </div>
        </form>
        <div class="mui-content-padded">
            <button type="button" class="mui-btn mui-btn-block mui-btn-success" @click="submit()">确认订单</button>
        </div>
    </div>
</template>

<script>
    import { Indicator } from 'mint-ui';
    import { Toast } from 'mint-ui';
    export default {
        data(){
            return {
                name: null,
                phone: null,
                address: null
            }
        },
        methods:{
            //提交时做非空校验,数据为空则弹出提示
            submit(){
                //默认值设为null,若未输入数据则仍为null，if判断为true；若传入了数据则不执行if
                if(!this.name) {
                    let instance = Toast('请输入姓名');
                    setTimeout(() => {
                        instance.close();
                    }, 3000)
                    return
                }
                if(!this.phone) {
                    let instance = Toast('请输入电话');
                    setTimeout(() => {
                        instance.close();
                    }, 3000)
                    return
                }
                if(!this.address) {
                    let instance = Toast('请输入地址');
                    setTimeout(() => {
                        instance.close();
                    }, 3000)
                    return
                }
                const _this = this
                //将用户信息和商品信息打包封装成一个orderForm对象传递给后端进行处理
                //id是用户id，不是商品id。 从本地取出用户信息，用JSON.parse（）转化为JSON格式 ，openid就是用户id
                //item是商品信息，从共享数据数组selectedArray中获取
                var orderForm ={
                    name: this.name,
                    phone: this.phone,
                    address: this.address,
                    id: JSON.parse(window.localStorage.getItem('user')).openid,
                    items: this.$store.state.selectedArray
                }

                Indicator.open('Loading...')
                axios.post('http://localhost:8383/order/buyer/order/create',orderForm).then(function(resp){
                    if(resp.data.code == 0){
                        Indicator.close()
                        _this.$router.push('/orderDetail?orderId='+resp.data.data.orderId)
                    }
                })
            }
        }
    }
</script>

<style scoped>

</style>