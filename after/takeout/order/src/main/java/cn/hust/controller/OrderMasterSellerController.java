package cn.hust.controller;

import cn.hust.entity.OrderMaster;
import cn.hust.service.OrderMasterService;
import cn.hust.utils.ResultVoUtil;
import cn.hust.vo.OrderPageVo;
import cn.hust.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller/order")
public class OrderMasterSellerController {

    @Autowired
    private OrderMasterService orderMasterService ;

    /**卖家端 查询订单
     *
     * @param page 在第page页展示
     * @param size 每页展示size个
     * @return VO
     */
    @GetMapping("/list/{page}/{size}")
    public ResultVo<OrderPageVo> list(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        OrderPageVo orderPageVo = this.orderMasterService.orderPageVo(page, size);
        if(orderPageVo == null){
            throw new RuntimeException(("查询异常！"));
        }
        return ResultVoUtil.success(orderPageVo);
    }


    /**取消订单
     *
     * @param id order_id
     * @return vo
     */
    @PutMapping("/cancel/{orderId}")
    public ResultVo cancel(@PathVariable("orderId")String id){
        OrderMaster orderMaster = this.orderMasterService.getById(id);
        orderMaster.setOrderStatus(2);
        boolean updated = this.orderMasterService.updateById(orderMaster);
        if(updated) return ResultVoUtil.success(null);
        return ResultVoUtil.fail();
    }

    /**完结订单
     *
     * @param id order_id
     * @return vo
     */
    @PutMapping("/finish/{orderId}")
    public ResultVo finish(@PathVariable("orderId")String id){
        OrderMaster orderMaster = this.orderMasterService.getById(id);
        orderMaster.setOrderStatus(1);
        boolean updated = this.orderMasterService.updateById(orderMaster);
        if(updated) return ResultVoUtil.success(null);
        return ResultVoUtil.fail();
    }

}
