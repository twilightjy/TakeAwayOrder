package cn.hust.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品表 实体类
 * </p>
 *
 * @author zz
 * @since 2021-04-06
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class ProductInfo implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "product_id", type = IdType.AUTO)
      private Integer productId;

      /**
     * 商品名称name
     */
      private String productName;

      /**
     * 商品单价price
     */
      private BigDecimal productPrice;

      /**
     * 库存stock
     */
      private Integer productStock;

      /**
     * 描述description
     */
      private String productDescription;

      /**
     * 小图icon
     */
      private String productIcon;

      /**
     * 类目编号type
     */
      private Integer categoryType;

      /**
     * 创建时间createTime
     */
        @TableField(fill = FieldFill.INSERT)
      private LocalDateTime createTime;

      /**
     * 修改时间updateTime
     */
        @TableField(fill = FieldFill.INSERT_UPDATE)
      private LocalDateTime updateTime;

      /**
     * 商品状态，1正常0下架 status
     */
      private Integer productStatus;


}
