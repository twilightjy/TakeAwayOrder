package cn.hust.service.impl;

import cn.hust.entity.ProductCategory;
import cn.hust.entity.ProductInfo;
import cn.hust.mapper.ProductCategoryMapper;
import cn.hust.mapper.ProductInfoMapper;
import cn.hust.service.ProductCategoryService;
import cn.hust.vo.ProductCategoryVo;
import cn.hust.vo.ProductInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 类目表 服务实现类
 * </p>
 *
 * @author zz
 * @since 2021-04-06
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper ;

    @Autowired
    private ProductInfoMapper productInfoMapper ;


    /* 查询商品信息
     * 1、查询所有的商品分类
     * 2、查询每个分类对应的商品信息
     * 3、根据VO进行数据封装
     */
    @Override
    public List<ProductCategoryVo> ProductCategoryVoList() {
        //获取全部商品分类（早午晚餐）,无需条件，直接查询;然后逐个封装成对应VO
        List<ProductCategory> productCategoryList = this.productCategoryMapper.selectList(null);
        //将商品分类VO装到一个集合中，也是最终的返回值
        List<ProductCategoryVo> productCategoryVoList = new ArrayList<>();
        //获取每一个分类对应的商品
        for (ProductCategory productCategory : productCategoryList) {
            //获取Type
            Integer categoryType = productCategory.getCategoryType();
            //将每个商品分类封装成VO
            ProductCategoryVo productCategoryVo = new ProductCategoryVo();//new VO
            productCategoryVo.setName(productCategory.getCategoryName());//设置name
            productCategoryVo.setType(productCategory.getCategoryType());//设置type
            //根据Type查商品信息，有条件查询，借助于QueryWrapper
            QueryWrapper<ProductInfo> wrapper = new QueryWrapper<>();
            wrapper.eq("category_type",categoryType);//传入查询条件，根据categoryType跨表查询
            //根据wrapper获取各个商品分类里的商品信息
            List<ProductInfo> productInfoList = this.productInfoMapper.selectList(wrapper);
            //将每个商品封装成VO,并加入到其分类下的List中
            List<ProductInfoVo> foods = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                ProductInfoVo productInfoVo = new ProductInfoVo();//new VO
//                productInfoVo.setProductId(productInfo.getProductId());//id
//                productInfoVo.setProductName(productInfo.getProductName());//name
//                productInfoVo.setProductIcon(productInfo.getProductIcon());//icon
//                productInfoVo.setProductPrice(productInfo.getProductPrice());//price
//                productInfoVo.setProductStock(productInfo.getProductStock());//stock
//                productInfoVo.setProductDescription(productInfo.getProductDescription());//description
                BeanUtils.copyProperties(productInfo,productInfoVo);//用工具类替代上面重复的赋值功能
                foods.add(productInfoVo);//加入到List中
            }
            productCategoryVo.setFoods(foods);//foods
            productCategoryVoList.add(productCategoryVo);
        }
        return productCategoryVoList;
    }

    /**
     * 只返回商品分类信息,不包含其下的foods
     * 即ProductCategoryVo中，只对name和type赋值了，foods没赋值
     * @return
     */
    @Override
    public List<ProductCategoryVo> onlyProductCategoryVoList() {
        //获取全部商品分类（早午晚餐）,无需条件，直接查询;然后逐个封装成对应VO
        List<ProductCategory> productCategoryList = this.productCategoryMapper.selectList(null);
        //将商品分类VO装到一个集合中，也是最终的返回值
        List<ProductCategoryVo> productCategoryVoList = new ArrayList<>();
        //获取每一个分类的name和type
        for (ProductCategory productCategory : productCategoryList) {
            //将每个商品分类封装成VO
            ProductCategoryVo productCategoryVo = new ProductCategoryVo();//new VO
            productCategoryVo.setName(productCategory.getCategoryName());//设置name
            productCategoryVo.setType(productCategory.getCategoryType());//设置type
            productCategoryVoList.add(productCategoryVo);
        }
        return productCategoryVoList;
    }


}
