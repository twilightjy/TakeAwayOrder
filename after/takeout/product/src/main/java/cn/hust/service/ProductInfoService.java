package cn.hust.service;

import cn.hust.entity.ProductInfo;
import cn.hust.vo.ProductInfoPageVo;
import cn.hust.vo.ProductInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author zz
 * @since 2021-04-06
 */
public interface ProductInfoService extends IService<ProductInfo> {
    public ProductInfoPageVo productInfoPageVo(Integer size,Integer total);

    public ProductInfoVo findById(Integer id);

    public boolean updateStatusById(Integer statusInt , Integer id);

}
