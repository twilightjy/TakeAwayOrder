<template>
    <div style="margin-top: 60px;margin-left:130px;width: 600px;height: 500px;border: 0px solid red;" >
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="商品名称" prop="name">
                <el-input v-model="ruleForm.name"></el-input>
            </el-form-item>
            <el-form-item label="商品价格" prop="price">
                <el-input v-model="ruleForm.price"></el-input>
            </el-form-item>
            <el-form-item label="商品库存" prop="stock">
                <el-input v-model.number="ruleForm.stock"></el-input>
            </el-form-item>
            <el-form-item label="商品描述" prop="description">
                <el-input v-model="ruleForm.description"></el-input>
            </el-form-item>
            <el-form-item label="商品图标" prop="icon">
                <el-input v-model="ruleForm.icon"></el-input>
            </el-form-item>

            <el-form-item label="商品分类" prop="ctype">
                <el-select style="margin-left: -276px" v-model="ruleForm.category.categoryType" placeholder="请选择分类">
                    <el-option v-for="item in category" :label="item.name" :value="item.type"></el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="商品状态" prop="productStatus">
                <el-switch
                        style="height: 20px;margin-left: -350px"
                        v-model="ruleForm.status"
                        active-color="#13ce66"
                        active-text="上架"
                        :active-value="true"
                        inactive-color="#ff4949"
                        inactive-text="下架"
                        :inactive-value="false"
                >
                </el-switch>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitForm('ruleForm')">修改</el-button>
                <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
        </el-form>
    </div>

</template>

<script>
    export default {
        data() {
            var valiNumber = (rule, value, callback) => {//包含小数的数字
                let reg = /^[+-]?(0|([1-9]\d*))(\.\d+)?$/g;
                if (value === '') {
                    callback(new Error('请输入商品价格'));
                } else if (!reg.test(value)) {
                    callback(new Error('价格必须为数值'));
                } else {
                    callback();
                }
            };
            return {
                category: null,
                ruleForm: null,
                rules: {
                    name: [
                        { required: true, message: '请输入商品名称', trigger: 'blur' },
                        { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
                    ],
                    price: [
                        { required: true, validator:valiNumber,trigger: 'blur' }
                    ],
                    stock: [
                        { required: true, message: '请输入商品库存', trigger: 'blur' },
                        { type: 'number', message: '库存必须为数值'}
                    ],
                    description: [
                        { required: true, message: '请输入商品描述', trigger: 'blur' },
                        { max: 10, message: '长度不能超过10个字符', trigger: 'blur' }
                    ],
                    icon: [
                        { required: true, message: '请输入商品图标链接', trigger: 'blur' }
                    ],
                    category: [
                        { required: true, message: '请选择商品分类', trigger: 'change' }
                    ],
                    status: [
                        { required: true, message: '请选择商品状态', trigger: 'change' }
                    ]
                }
            };
        },
        methods: {
            submitForm(formName) {
                const _this = this
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        axios.put('http://localhost:8383/product/seller/product/update',_this.ruleForm).then(function(resp){
                            if(resp.data.code == 0){
                                _this.$alert(_this.ruleForm.name+'修改成功', '', {
                                    confirmButtonText: '确定',
                                    callback: action => {
                                        _this.$router.push('/productManage')
                                    }
                                });
                            }
                        })
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            }
        },
        created() {
            const _this = this
            axios.get('http://localhost:8383/product/seller/product/findAllProductCategory').then(function(resp){
                _this.category = resp.data.data.content
            })
            axios.get('http://localhost:8383/product/seller/product/findById/'+this.$route.query.id).then(function(resp){
                _this.ruleForm = resp.data.data
            })
        }
    }
</script>
