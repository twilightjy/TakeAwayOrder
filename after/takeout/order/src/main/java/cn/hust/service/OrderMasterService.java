package cn.hust.service;

import cn.hust.entity.OrderMaster;
import cn.hust.form.OrderForm;
import cn.hust.vo.OrderPageVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author zz
 * @since 2021-04-08
 */
public interface OrderMasterService extends IService<OrderMaster> {
    public boolean insert(OrderForm orderForm) ;

    public OrderPageVo orderPageVo(Integer page , Integer size);
}
