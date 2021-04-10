package cn.hust.controller;


import cn.hust.entity.ProductInfo;
import cn.hust.service.ProductCategoryService;
import cn.hust.service.ProductInfoService;
import cn.hust.utils.ResultVoUtil;
import cn.hust.vo.ProductCategoryVo;
import cn.hust.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 * @author zz
 * @since 2021-04-06
 */
@RestController
@RequestMapping("/buyer/product")
@Slf4j
public class ProductInfoBuyerController {

    @Autowired
    private ProductCategoryService productCategoryService ;

    @Autowired
    private ProductInfoService productInfoService;

    /**商品列表
     * List<ProductCategoryVo> 是ResultVo<T> 的泛型声明
     * @return 商品列表
     */
    @GetMapping("/list")
    public ResultVo<List<ProductCategoryVo>> list(){
        ResultVo<List<ProductCategoryVo>> resultVo = new ResultVo<>();
        resultVo.setCode(0);
        resultVo.setMsg("成功");
        resultVo.setData(this.productCategoryService.ProductCategoryVoList());
        return resultVo;
    }

    /**
     *
     * @param id productId
     * @return 根据id在product_info表中查到对应行的信息
     */
    @GetMapping("/findById/{id}")
    public ProductInfo findById(@PathVariable("id") Integer id){
        return this.productInfoService.getById(id);
    }


    /**
     *修改stock 通过Feign被调用
     * 调用者：OrderMasterServiceImpl中的insert方法
     * @param id productId
     * @param quantity 订单中某样商品的数量（客户需求量）
     * @return 根据商品quantity和库存关系进行对库存stock的修改
     */
    @PutMapping("/subStock/{id}/{quantity}")
    public boolean subStock(@PathVariable("id") Integer id , @PathVariable("quantity") Integer quantity){
        ProductInfo productInfo = this.productInfoService.getById(id);//首先根据id找到商品信息
        //若商品不存在则抛出异常
        if(productInfo == null){
            log.info("商品不存在,id={}",id);
            throw new RuntimeException("商品不存在！");
        }else {
            Integer stock = productInfo.getProductStock();//获取库存
            int result = stock - quantity;
            if(result < 0){
                log.info("库存不足,stock={}",stock);
                throw new RuntimeException("商品库存不足！");
            }else {
                productInfo.setProductStock(result);
                return this.productInfoService.updateById(productInfo);//若不符合规则会在之前就抛出异常，全部正常才来到这里返回最后的存储结果，所以最后不需要再return false
            }
        }
    }




}



