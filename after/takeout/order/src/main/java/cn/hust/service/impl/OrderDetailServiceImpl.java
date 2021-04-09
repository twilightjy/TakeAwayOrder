package cn.hust.service.impl;

import cn.hust.entity.OrderDetail;
import cn.hust.mapper.OrderDetailMapper;
import cn.hust.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单详情表 服务实现类
 * </p>
 *
 * @author zz
 * @since 2021-04-08
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}
