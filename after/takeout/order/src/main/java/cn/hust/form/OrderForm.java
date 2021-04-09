package cn.hust.form;

import cn.hust.entity.OrderDetail;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

@Data
public class OrderForm {

    @NotEmpty(message = "买家姓名不能为空")
    private String name;

    @NotEmpty(message = "电话不能为空")
    private String phone;

    @NotEmpty(message = "地址不能为空")
    private String address;

    @NotEmpty(message = "openid不能为空")
    private String id;

    /*items中的属性在OrderDetail实体类中全部都有*/
    @NotEmpty(message = "购物车不能为空")
    private List<OrderDetail> items;
}

/*前端传来的JSON格式参数：
 * {
 *   	"name": "张三"
 *    	"phone": "13678987656"
 *     "address": "科技路"
 *     "id": "ax001"
 *     "items": [{
 *         "productId": 1,
 *         "productQuantity": 2
 *     }，
 *         "productId": 1,
 *         "productQuantity": 2
 *     }]
 * }
 */
