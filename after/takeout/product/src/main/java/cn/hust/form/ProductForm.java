package cn.hust.form;

import cn.hust.entity.ProductCategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;

/**
 * JSON格式参数封装成Form
 */
@Data
public class ProductForm {

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
     * 商品库存 stock
     */
    @JsonProperty("stock")
    private Integer productStock ;

    /*
     *
     */
    @JsonProperty("status")
    private boolean productStatus ;

    /**
     * ProductCategory实体类
     */
    @JsonProperty("category")
    private ProductCategory productCategory ;


}

/*假数据格式
 {
 "status": true,
 "id": 1,
 "name": "肉夹馍",
 "price": 16,
 "stock": 107,
 "description": "好吃好吃",
 "icon": "https://s1.st.meishij.net/rs/50/123/6030800/n6030800_152708155351112.jpg",
 "category": {
    "categoryId": 1,
    "categoryName": "热销榜",
    "categoryType": 2
 }
 }*/