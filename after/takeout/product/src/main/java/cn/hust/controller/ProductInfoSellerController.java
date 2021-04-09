package cn.hust.controller;

import cn.hust.entity.ProductInfo;
import cn.hust.service.ProductCategoryService;
import cn.hust.service.ProductInfoService;
import cn.hust.utils.ResultVoUtil;
import cn.hust.vo.ProductCategoryVo;
import cn.hust.vo.ProductInfoPageVo;
import cn.hust.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/seller/product")
public class ProductInfoSellerController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;


    /**查询所有商品分类 （不包含商品信息）
     *
     * @return 1
     */
    @GetMapping("/findAllProductCategory")
    public ResultVo<Map<String, List<ProductCategoryVo>>> findAllProductCategory(){
        List<ProductCategoryVo> productCategoryVoList = this.productCategoryService.onlyProductCategoryVoList();
        Map<String,List<ProductCategoryVo>> map = new HashMap<>();
        map.put("content",productCategoryVoList);
        return ResultVoUtil.success(map);
    }

    /**
     *
     * @param productInfo 1
     * @return 1
     */
    @PostMapping("/add")
    public ResultVo add(@RequestBody ProductInfo productInfo){
        boolean saved = this.productInfoService.save(productInfo);
        if(saved) return ResultVoUtil.success(null);
        return ResultVoUtil.fail();
    }

    /**
     *
     * @param page 1
     * @param size 1
     * @return 1
     */
    @GetMapping("/list/{page}/{size}")
    public ResultVo<ProductInfoPageVo> list(@PathVariable("page") Integer page,@PathVariable("size") Integer size){
        ProductInfoPageVo productInfoPageVo = this.productInfoService.productInfoPageVo(page, size);
        if(productInfoPageVo == null) {
            throw new RuntimeException("查询异常！");
        }
        return ResultVoUtil.success(productInfoPageVo);
    }


}
