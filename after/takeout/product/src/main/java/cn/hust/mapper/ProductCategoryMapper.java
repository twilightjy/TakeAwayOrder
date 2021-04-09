package cn.hust.mapper;

import cn.hust.entity.ProductCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 类目表 Mapper 接口
 * </p>
 *
 * @author zz
 * @since 2021-04-06
 */
@Repository
public interface ProductCategoryMapper extends BaseMapper<ProductCategory> {
    //根据categoryType查询categoryName
    public String getCategoryNameByType(Integer type);
}
