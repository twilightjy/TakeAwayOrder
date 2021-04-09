package cn.hust.vo;
/*通过id查询商品API中
 *根据size和total对content进行分页展示
 */

import lombok.Data;
import java.util.List;

@Data
public class ProductInfoPageVo {
    private List<ProductInfoVo> content ;
    private Long size ;
    private Long total ;

}
