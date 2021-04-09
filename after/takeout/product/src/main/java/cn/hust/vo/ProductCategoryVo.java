package cn.hust.vo;
/**
 * <p>
 * 自定义VO
 * 对应于前端数据中 data 部分
 * </p>
 *
 * @author zz
 * @since 2021-04-08
 */
import lombok.Data;

import java.util.List;

@Data
public class ProductCategoryVo {

    /* */
    private String name ;

    /* */
    private Integer type ;

    /*有多个所以是List foods*
     *ProductInfoVo 是根据foods的七个属性写的VO
     */
    private List<ProductInfoVo> foods;

}
