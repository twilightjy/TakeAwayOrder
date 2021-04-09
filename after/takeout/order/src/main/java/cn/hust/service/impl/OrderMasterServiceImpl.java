package cn.hust.service.impl;

import cn.hust.entity.OrderDetail;
import cn.hust.entity.OrderMaster;
import cn.hust.entity.ProductInfo;
import cn.hust.feign.ProductFeignClient;
import cn.hust.form.OrderForm;
import cn.hust.mapper.OrderDetailMapper;
import cn.hust.mapper.OrderMasterMapper;
import cn.hust.mapper.ProductInfoMapper;
import cn.hust.service.OrderMasterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author zz
 * @since 2021-04-08
 */
@Service
public class OrderMasterServiceImpl extends ServiceImpl<OrderMasterMapper, OrderMaster> implements OrderMasterService {

    @Autowired
    private OrderMasterMapper orderMasterMapper ;

    @Autowired
    private ProductInfoMapper productInfoMapper ;

    @Autowired
    private ProductFeignClient productFeignClient ;

    @Autowired
    private OrderDetailMapper orderDetailMapper ;

    /**
     * 创建订单 需要修改order_master order_detail product_info 三张表
     * @param orderForm 1
     * @return 1
     */
    @Override
    public boolean insert(OrderForm orderForm) {
        //以下操作,orderForm转化为orderMaster
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setBuyerName(orderForm.getName());
        orderMaster.setBuyerOpenid(orderForm.getId());
        orderMaster.setBuyerAddress(orderForm.getAddress());
        orderMaster.setBuyerPhone(orderForm.getPhone());
        orderMaster.setOrderStatus(0);
        orderMaster.setPayStatus(0);
        //计算order_amount 订单总价 根据item的id在product_info中选出相应商品，然后获取单价price，与item的quantity数量相乘即可。
        List<OrderDetail> items = orderForm.getItems();//从orderForm中获取items 集合 ,然后遍历
        BigDecimal amount = new BigDecimal(0);
        for (OrderDetail item : items) {
            //借助QueryWrapper根据productId在product_info表中查询单价
            QueryWrapper<ProductInfo> wrapper = new QueryWrapper<>();
            wrapper.eq("product_id",item.getProductId());
            ProductInfo productInfo = this.productInfoMapper.selectOne(wrapper);
            //注意BigDecimal类型数据的运算，它不识别基本的运算符，需要通过方法 而且运算完后还要用 = 重新赋值
            amount = amount.add(productInfo.getProductPrice().multiply(new BigDecimal(item.getProductQuantity())));
        }
        orderMaster.setOrderAmount(amount);//设置order_amount
        //以上操作后，存入order_master表
        int insert = this.orderMasterMapper.insert(orderMaster);//insert为1表示添加新订单成功
        //当order_master存入成功后,以下操作，开始存入order_detail表
        if(insert == 1){
            for (OrderDetail item : items) {
                //通过product_id查询商品信息
                Integer productId = item.getProductId();
                ProductInfo productInfo = this.productFeignClient.findById(productId);//获取productInfo
                BeanUtils.copyProperties(productInfo,item);//将productInfo的值赋给item,除了orderId都在数据库中能取到
                item.setOrderId(orderMaster.getOrderId());//orderId是在上面存入orderMaster后系统生成的，此处取出
                try {
                    this.orderDetailMapper.insert(item);//存入orderDetail
                } catch (Exception e) {
                    throw new RuntimeException("订单详情添加失败");
                }
                try {
                    this.productFeignClient.subStock(productId, item.getProductQuantity());//修改库存,修改product_info
                } catch (Exception e) {
                    throw new RuntimeException(productInfo.getProductName()+"库存不足!");
                }
            }
        }

        return insert == 1;
    }
}
