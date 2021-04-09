package cn.hust.service;

import cn.hust.entity.ProductCategory;
import cn.hust.vo.ProductCategoryVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 类目表 服务类
 * </p>
 *
 * @author zz
 * @since 2021-04-06
 */
public interface ProductCategoryService extends IService<ProductCategory> {

    /*查询全部分类下的全部商品信息*/
    public List<ProductCategoryVo> ProductCategoryVoList();

    /*只返回商品分类信息,不包含其下的foods*/
    public List<ProductCategoryVo> onlyProductCategoryVoList();

}
