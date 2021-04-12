<template>
    <div style="margin-top: 60px;margin-left:80px;border: 0px solid red;" >
        <el-table
                :data="tableData"
                border
                style="width: 100%">
            <el-table-column
                    fixed
                    prop="id"
                    label="编号"
                    width="90">
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="商品名称"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="price"
                    label="商品价格"
                    width="90">
            </el-table-column>
            <el-table-column
                    prop="stock"
                    label="商品库存"
                    width="90">
            </el-table-column>
            <el-table-column
                    prop="description"
                    label="商品描述"
                    width="150">
            </el-table-column>
            <el-table-column label="商品图标" width="130">
                　　<template slot-scope="scope">
                　　　　<img :src="scope.row.icon" width="60"/>
                　　</template>
            </el-table-column>
            <el-table-column label="上架" width="160">

                <template slot-scope="scope">
                    <el-switch
                            style="height: 20px"
                            v-model="scope.row.status"
                            active-color="#13ce66"
                            active-text="上架"
                            inactive-color="#ff4949"
                            inactive-text="下架"
                            @change="changeSwitch(scope.row)">
                    </el-switch>
                </template>

            </el-table-column>
            <el-table-column
                    prop="categoryName"
                    label="商品分类"
                    width="130">
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            @click="edit(scope.row)">编辑</el-button>
                    <el-button
                            size="mini"
                            type="danger"
                            @click="del(scope.row)">删除</el-button>
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
                total:20,
                value1: '1'
            }
        },

        methods:{
            //分页功能 currentPage点击获取当前页面号，和create一样从后台调，page换为currentPage
            page(currentPage){
                const _this = this
                axios.get('http://localhost:8383/product/seller/product/list/'+currentPage+'/'+_this.pageSize).then(function (resp) {
                    _this.tableData = resp.data.data.content
                    _this.pageSize = resp.data.data.size
                    _this.total = resp.data.data.total
                })
            },
            edit(row){
                this.$router.push('/editProduct?id='+row.id)
            },
            del(row) {
                const _this = this
                this.$confirm('确认删除【'+row.name+'】吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    axios.delete('http://localhost:8383/product/seller/product/delete/'+row.id).then(function (resp) {
                        if(resp.data.code==0){
                            _this.$alert('【'+row.name+'】已删除', '', {
                                confirmButtonText: '确定',
                                callback: action => {
                                    location.reload()
                                }
                            });
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            changeSwitch (data) {
                console.log(data)
                const _this = this
                axios.put('http://localhost:8383/product/seller/product/updateStatus/'+data.id+"/"+data.status).then(function (resp) {
                    if(resp.data.data == true){
                        _this.$message({
                            showClose: true,
                            message: '【'+data.name+'】已上架',
                            type: 'success'
                        })
                    }else{
                        _this.$message({
                            showClose: true,
                            message: '【'+data.name+'】已下架',
                            type: 'error'
                        })
                    }
                })
            }
        },

        created() {
            const _this = this
            axios.get('http://localhost:8383/product/seller/product/list/1/'+_this.pageSize).then(function (resp) {
                _this.tableData = resp.data.data.content
                _this.pageSize = resp.data.data.size
                _this.total = resp.data.data.total
            })
        }
    }
</script>