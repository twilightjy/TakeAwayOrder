package cn.hust.vo;

import cn.hust.entity.OrderMaster;
import lombok.Data;

import java.util.List;

/**
 * 卖家端 查询订单 返回参数封装成OrderPageVO
 */
@Data
public class OrderPageVo {

    private List<OrderMaster> content ;

    private Long size ;
    private Long total ;

}
