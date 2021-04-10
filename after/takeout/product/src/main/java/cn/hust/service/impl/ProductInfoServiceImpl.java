package cn.hust.service.impl;

import cn.hust.entity.ProductCategory;
import cn.hust.entity.ProductInfo;
import cn.hust.mapper.ProductCategoryMapper;
import cn.hust.mapper.ProductInfoMapper;
import cn.hust.service.ProductInfoService;
import cn.hust.vo.ProductInfoPageVo;
import cn.hust.vo.ProductInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author zz
 * @since 2021-04-06
 */
@Service
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo> implements ProductInfoService {

    @Autowired
    private ProductInfoMapper productInfoMapper ;

    @Autowired
    private ProductCategoryMapper productCategoryMapper ;

    /**
     *
     * @param page 分页 在第page页展示
     * @param size 每页展示的数量
     * @return PageVo 涉及page的操作注意细节
     */
    @Override
    public ProductInfoPageVo productInfoPageVo(Integer page, Integer size) {
        //封装分页对象
        Page<ProductInfo> productInfoPage = new Page<>(page,size);
        Page<ProductInfo> selectPage = this.productInfoMapper.selectPage(productInfoPage, null);
        //
        List<ProductInfo> records = selectPage.getRecords();
        //根据productInfo 即record, 封装成productInfoVo
        List<ProductInfoVo> productInfoVoList = new ArrayList<>();
        for (ProductInfo record : records) {
            ProductInfoVo productInfoVo = new ProductInfoVo();
            BeanUtils.copyProperties(record,productInfoVo);
            //设置status
            if(record.getProductStatus() == 1) {
                productInfoVo.setStatus(true);
            }else {
                productInfoVo.setStatus(false);
            }
            //根据categoryType在product_category表中找到商品所属的分类，然后获取分类名称categoryName
            QueryWrapper<ProductCategory> wrapper = new QueryWrapper<>();
            wrapper.eq("category_type",record.getCategoryType());

//            ProductCategory productCategory = this.productCategoryMapper.selectOne(wrapper);
//            //这里其实只需要productCategory中的name属性，却将整个行查出来了，所以存在浪费，这是直接使用MyBatisPlus的方法造成的。
//            //要想仅查找指定的字段，需要自定义mapper接口方法,自己写xml实现。改写:
//            productInfoVo.setCategoryName(productCategory.getCategoryName());

            String categoryName = this.productCategoryMapper.getCategoryNameByType(record.getCategoryType());
            productInfoVo.setCategoryName(categoryName);
            productInfoVoList.add(productInfoVo);
        }
        long size1 = selectPage.getSize();
        long total1 = selectPage.getTotal();
        ProductInfoPageVo productInfoPageVo = new ProductInfoPageVo();
        productInfoPageVo.setContent(productInfoVoList);
        productInfoPageVo.setTotal(total1);
        productInfoPageVo.setSize(size1);
        return productInfoPageVo;
    }


    /**通过id查询商品
     * 封装data对应的VO
     * @param id
     * @return
     */
    @Override
    public ProductInfoVo findById(Integer id) {
        ProductInfo productInfo = this.productInfoMapper.selectById(id);
        ProductInfoVo productInfoVo = new ProductInfoVo();
        BeanUtils.copyProperties(productInfo,productInfoVo);
        if(productInfo.getProductStatus() == 1){
            productInfoVo.setStatus(true);
        }else {
            productInfoVo.setStatus(false);
        }
        Map<String,Integer> category = new HashMap<>();
        category.put("categoryType",productInfo.getCategoryType());
        productInfoVo.setCategory(category);
        return productInfoVo;
    }

    /**
     * 根据product_id修改product_info表中对应行的product_status
     * @param id product_id
     * @return 修改成功与否
     */
    @Override
    public boolean updateStatusById(Integer statusInt , Integer id) {
        int i = this.productInfoMapper.updateStatusById(statusInt, id);
        return i == 1;
    }
}
