package cn.hust.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author zz
 * @since 2021-04-08
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class OrderMaster implements Serializable {

    private static final long serialVersionUID=1L;

    /*订单号orderId交由mybatisPlus自动生成一个由数字和字符组成的32位字符串，无需手动赋值*/
    @TableId(type = IdType.ASSIGN_UUID)
      private String orderId;

      /**
     * 买家名字
     */
      private String buyerName;

      /**
     * 买家电话
     */
      private String buyerPhone;

      /**
     * 买家地址
     */
      private String buyerAddress;

      /**
     * 买家微信openid
     */
      private String buyerOpenid;

      /**
     * 订单总金额
     */
      private BigDecimal orderAmount;

      /**
     * 订单状态，默认0新下单,1完成，2取消
     */
      private Integer orderStatus;

      /**
     * 支付状态，默认0未支付，1已支付
     */
      private Integer payStatus;

      /**
     * 创建时间
     */
        @TableField(fill = FieldFill.INSERT)
      private LocalDateTime createTime;

      /**
     * 修改时间
     */
        @TableField(fill = FieldFill.INSERT_UPDATE)
      private LocalDateTime updateTime;


}
