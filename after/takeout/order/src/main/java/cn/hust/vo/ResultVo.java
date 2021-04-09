package cn.hust.vo;

import lombok.Data;

/**
 * <p>
 *  View Of Object
 *  根据前端data的格式，转化封装为Java对象
 * </p>
 *
 * @author zz
 * @since 2021-04-08
 */

@Data
public class ResultVo<T> {

    /*状态码*/
    private Integer code ;

    /*状态信息*/
    private String msg ;

    /*data的具体内容不完全一样，所以用泛型T代替 */
    private T data;

}

/*假数据格式
{
  "code": 0,
  "msg": "成功",
  "data": {
      "orderId": "1580478626065915735"
  }
}
 */