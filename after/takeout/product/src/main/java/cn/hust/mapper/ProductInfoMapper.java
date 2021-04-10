package cn.hust.mapper;

import cn.hust.entity.ProductInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author zz
 * @since 2021-04-06
 */
@Repository
public interface ProductInfoMapper extends BaseMapper<ProductInfo> {
     public int updateStatusById(Integer statusInt , Integer id) ;
}
