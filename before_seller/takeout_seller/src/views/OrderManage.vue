<template>
    <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;" >
        <el-table
                :data="tableData"
                border
                style="width: 100%">
            <el-table-column
                    fixed
                    prop="orderId"
                    label="订单编号"
                    width="170">
            </el-table-column>
            <el-table-column
                    prop="buyerName"
                    label="买家姓名"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="buyerPhone"
                    label="联系电话"
                    width="130">
            </el-table-column>
            <el-table-column
                    prop="buyerAddress"
                    label="收货地址"
                    width="170">
            </el-table-column>
            <el-table-column
                    prop="orderAmount"
                    label="订单总价"
                    width="80">
            </el-table-column>
            <el-table-column
                    label="订单状态"
                    width="80">
                <template slot-scope="scope">
                    {{transformOrderStatus(scope.row.orderStatus)}}
                </template>
            </el-table-column>
            <el-table-column
                    label="支付状态"
                    width="80">
                <template slot-scope="scope">
                    {{transformPayStatus(scope.row.payStatus)}}
                </template>
            </el-table-column>
            <el-table-column label="创建时间"
                    width="160">
                <template slot-scope="scope">
                    {{dateFormat(scope.row.createTime)}}
                </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
                <template slot-scope="scope">
                    <el-button
                            :disabled="scope.row.orderStatus==2 || scope.row.orderStatus==1"
                            size="mini"
                            type="danger"
                            @click="cancel(scope.row)">取消</el-button>
                    <el-button
                            :disabled="scope.row.orderStatus==1 || scope.row.orderStatus==2"
                            size="mini"
                            type="success"
                            @click="finish(scope.row)">完成</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination style="margin-top: 20px;float: right"
                       background
                       layout="prev, pager, next"
                       :page-size="pageSize"
                       :total="total"
                       @current-change="page">
        </el-pagination>
    </div>

</template>

<script>
    export default {
        data() {
            return {
                tableData:null,
                pageSize:5,
                total:null,
            }
        },
        methods:{
            transformOrderStatus(status){
                switch (status){
                    case 0: return '新订单';break;
                    case 1: return '已完成';break;
                    case 2: return '已取消';break;
                }
            },
            transformPayStatus(status){
                switch (status){
                    case 0: return '未支付';break;
                    case 1: return '已支付';break;
                }
            },
            dateFormat(time) {
                var date = new Date(time);
                var year=date.getFullYear();
                var month= date.getMonth()+1<10 ? "0"+(date.getMonth()+1) : date.getMonth()+1;
                var day=date.getDate()<10 ? "0"+date.getDate() : date.getDate();
                var hours=date.getHours()<10 ? "0"+date.getHours() : date.getHours();
                var minutes=date.getMinutes()<10 ? "0"+date.getMinutes() : date.getMinutes();
                var seconds=date.getSeconds()<10 ? "0"+date.getSeconds() : date.getSeconds();
                return year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
            },
            page(currentPage){
                const _this = this
                axios.get('http://localhost:8383/order/seller/order/list/'+currentPage+'/'+_this.pageSize).then(function (resp) {
                    _this.tableData = resp.data.data.content
                    _this.pageSize = resp.data.data.size
                    _this.total = resp.data.data.total
                })
            },
            cancel(row) {
                const _this = this
                axios.put('http://localhost:8383/order/seller/order/cancel/'+row.orderId).then(function (resp) {
                    if(resp.data.code == 1){
                        _this.$alert('订单【'+row.orderId+'】状态异常', '错误', {
                            confirmButtonText: '确定'
                        });
                    }
                    if(resp.data.code == 0){
                        _this.$alert('订单【'+row.orderId+'】已取消', '成功', {
                            confirmButtonText: '确定',
                            callback: action => {
                                location.reload()
                            }
                        });
                    }
                })
            },
            finish(row) {
                if(row.payStatus == 0){
                    this.$alert('订单【'+row.orderId+'】未支付，无法完成该订单', '错误', {
                        confirmButtonText: '确定'
                    });
                }else if(row.orderStatus == 2){
                    this.$alert('订单【'+row.orderId+'】已取消，无法完成该订单', '错误', {
                        confirmButtonText: '确定',
                    });
                }else{
                    const _this = this
                    axios.put('http://localhost:8383/order/seller/order/finish/'+row.orderId).then(function (resp) {
                        if(resp.data.code == 1){
                            _this.$alert('订单【'+row.orderId+'】状态异常', '错误', {
                                confirmButtonText: '确定'
                            });
                        }
                        if(resp.data.code == 0){
                            _this.$alert('订单【'+row.orderId+'】已完成', '成功', {
                                confirmButtonText: '确定',
                                callback: action => {
                                    location.reload()
                                }
                            });
                        }
                    })
                }
            }
        },
        created() {
            const _this = this
            axios.get('http://localhost:8383/order/seller/order/list/1/'+_this.pageSize).then(function (resp) {
                console.log(resp.data)
                _this.tableData = resp.data.data.content
                _this.pageSize = resp.data.data.size
                _this.total = resp.data.data.total
            })
        }
    }
</script>