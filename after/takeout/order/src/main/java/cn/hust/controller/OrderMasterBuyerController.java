package cn.hust.controller;


import cn.hust.entity.OrderDetail;
import cn.hust.entity.OrderMaster;
import cn.hust.form.OrderForm;
import cn.hust.service.OrderDetailService;
import cn.hust.service.OrderMasterService;
import cn.hust.utils.ResultVoUtil;
import cn.hust.vo.OrderMasterVo;
import cn.hust.vo.ResultVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author zz
 * @since 2021-04-08
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class OrderMasterBuyerController {

    @Autowired
    private OrderMasterService orderMasterService ;

    @Autowired
    private OrderDetailService orderDetailService ;

    /**创建订单 需要修改order_master order_detail product_info 三张表
     *
     * @param orderForm 前端JSON格式的form转化封装为orderForm
     * @return VO
     */
    @PostMapping("/create")
    public ResultVo<Map<String,String>> create(@Valid @RequestBody OrderForm orderForm , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("【创建订单】,参数异常，orderForm={}",orderForm);//{}占位符 orderForm会填充到其中
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        }
        //order_master添加数据
        String orderId = this.orderMasterService.insert(orderForm);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",orderId);
        return ResultVoUtil.success(map);
    }

    /**订单列表
     *
     * @param buyerId buyer_openid
     * @return resultVo 一个买家的全部订单详情
     */
    @GetMapping("/list/{buyerId}")
    public ResultVo<List<OrderMasterVo>> list(@PathVariable("buyerId") String buyerId){
        //buyerId对应于buyer_openid,例如微信每个人有微信号，但是微信号还和一个openid绑定，也是一个字符+数字的32位组合
        //一个买家可能对应多个订单，所以一个buyer_id可以查到多个order_id，借助于wrapper
        QueryWrapper<OrderMaster> wrapper = new QueryWrapper<>();
        wrapper.eq("buyer_openid",buyerId);
        List<OrderMaster> orderMasterList = this.orderMasterService.list(wrapper);//根据buyer_openid在order_master表中查出所有关联行
        List<OrderMasterVo> orderMasterVoList = new ArrayList<>();//由于API中data部分在OrderMaster基础上多了一个OrderDetailList属性，所以要封装一个VO
        for (OrderMaster orderMaster : orderMasterList) {
            OrderMasterVo orderMasterVo = new OrderMasterVo();
            BeanUtils.copyProperties(orderMaster,orderMasterVo);//根据API,OrderDetailList暂时为null
            orderMasterVoList.add(orderMasterVo);
        }
        return ResultVoUtil.success(orderMasterVoList);
    }


    /**查询订单详情
     *
     * 基于上面的list方法，对OrderDetailList进行了赋值
     * @param buyerId 1
     * @param orderId 1
     * @return 1
     */
    @GetMapping("/detail/{buyerId}/{orderId}")
    public ResultVo<OrderMasterVo> detail(@PathVariable("buyerId") String buyerId, @PathVariable("orderId") String orderId){
        //buyerId对应于buyer_openid,例如微信每个人有微信号，但是微信号还和一个openid绑定，也是一个字符+数字的32位组合
        //buyer_id+order_id对应唯一的order_master数据行，借助于wrapper
        QueryWrapper<OrderMaster> wrapper = new QueryWrapper<>();
        wrapper.eq("buyer_openid",buyerId);
        wrapper.eq("order_id",orderId);
        OrderMaster orderMaster1 = this.orderMasterService.getOne(wrapper);//getOne 唯一数据
        OrderMasterVo orderMasterVo = new OrderMasterVo();
        BeanUtils.copyProperties(orderMaster1,orderMasterVo);
        //查询orderDetail
        QueryWrapper<OrderDetail> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("order_id",orderId);
        List<OrderDetail> orderDetailList = this.orderDetailService.list(wrapper1);
        orderMasterVo.setOrderDetailList(orderDetailList);
        return ResultVoUtil.success(orderMasterVo);
    }


    /**取消订单
     *
     * @param buyerId 1
     * @param orderId 1
     * @return 1
     */
    @PutMapping("/cancel/{buyerId}/{orderId}")
    public ResultVo cancel(@PathVariable("buyerId") String buyerId,@PathVariable("orderId") String orderId){
        QueryWrapper<OrderMaster> wrapper = new QueryWrapper<>();
        wrapper.eq("buyer_openid",buyerId);
        wrapper.eq("order_id",orderId);
        OrderMaster orderMaster = this.orderMasterService.getOne(wrapper);
        if(orderMaster == null){
            throw new RuntimeException("订单不存在!");
        }
        if(orderMaster.getOrderStatus() != 0){
            throw new RuntimeException("订单状态错误！");
        }
        orderMaster.setOrderStatus(2);
        boolean b = this.orderMasterService.updateById(orderMaster);
        if(b) return ResultVoUtil.success(null);
        return ResultVoUtil.fail();
    }


    /**完结订单
     *
     * @param orderId 1
     * @return 1
     */
    @PutMapping("/finish/{orderId}")
    public ResultVo finish(@PathVariable("orderId") String orderId){
        QueryWrapper<OrderMaster> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id",orderId);
        OrderMaster orderMaster = this.orderMasterService.getOne(wrapper);
        if(orderMaster == null){
            throw new RuntimeException("订单不存在!");
        }
        if(orderMaster.getOrderStatus() != 0){
            throw new RuntimeException("订单状态错误！");
        }
        orderMaster.setOrderStatus(1);
        boolean b = this.orderMasterService.updateById(orderMaster);
        if(b) return ResultVoUtil.success(null);
        return ResultVoUtil.fail();
    }

    /**支付订单
     *
     * @param buyerId 1
     * @param orderId 1
     * @return 1
     */
    @PutMapping("/pay/{buyerId}/{orderId}")
    public ResultVo pay(@PathVariable("buyerId") String buyerId,@PathVariable("orderId") String orderId){
        QueryWrapper<OrderMaster> wrapper = new QueryWrapper<>();
        wrapper.eq("buyer_openid",buyerId);
        wrapper.eq("order_id",orderId);
        OrderMaster orderMaster = this.orderMasterService.getOne(wrapper);
        if(orderMaster == null){
            throw new RuntimeException("订单不存在!");
        }
        if(orderMaster.getPayStatus() != 0){
            throw new RuntimeException("支付状态错误！");
        }
        orderMaster.setPayStatus(1);
        boolean b = this.orderMasterService.updateById(orderMaster);
        if(b) return ResultVoUtil.success(null);
        return ResultVoUtil.fail();
    }


}

