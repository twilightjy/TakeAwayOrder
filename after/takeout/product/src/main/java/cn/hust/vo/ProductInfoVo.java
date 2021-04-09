package cn.hust.vo;
/**
 * <p>
 * 商品信息VO
 * 由于根据数据库建立的实体类和前端的data的格式不完全匹配，所以需要自己根据data写VO
 * </p>
 *
 * @author zz
 * @since 2021-04-08
 */
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInfoVo {

    /**
     * 商品id
     */
    @JsonProperty("id")
    private Integer productId ;

    /**
     * 商品名称name
     */
    @JsonProperty("name")
    private String productName ;

    /**
     * 商品价格price 不用double 而用定点型更精确的BigDecimal类型
     */
    @JsonProperty("price")
    private BigDecimal productPrice ;

    /**
     * 商品描述description
     */
    @JsonProperty("description")
    private String productDescription ;

    /**
     * 商品图片 网址 icon
     */
    @JsonProperty("icon")
    private String productIcon ;

    /**
     * 商品数量 quantity
     */
    @JsonProperty("quantity")
    private Integer productQuantity = 0 ;

    /**
     * 商品库存 stock
     */
    @JsonProperty("stock")
    private Integer productStock ;

    /*
     *通过id查询 商品的status
     */
    private boolean status ;

    /*
     *通过id查询 商品的categoryName 在product_category表中
     */
    private String categoryName;

}
