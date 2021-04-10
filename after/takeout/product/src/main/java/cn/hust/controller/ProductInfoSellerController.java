package cn.hust.controller;

import cn.hust.entity.ProductInfo;
import cn.hust.form.ProductForm;
import cn.hust.service.ProductCategoryService;
import cn.hust.service.ProductInfoService;
import cn.hust.utils.ResultVoUtil;
import cn.hust.vo.ProductCategoryVo;
import cn.hust.vo.ProductInfoPageVo;
import cn.hust.vo.ProductInfoVo;
import cn.hust.vo.ResultVo;
import org.springframework.beans.BeanUtils;
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

    /**添加商品
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

    /**查询商品
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

    /**通过id查询商品
     *
     * @param id productInfo中的product_id
     * @return VO
     */
    @GetMapping("/findById/{id}")
    public ResultVo<ProductInfoVo> findById(@PathVariable("id")Integer id){
        ProductInfoVo productInfoVo = this.productInfoService.findById(id);
        if(productInfoVo == null) {
            throw new RuntimeException("查询异常！");
        }
        return ResultVoUtil.success(productInfoVo);
    }

    /**通过id删除商品
     *
     * @param id productInfo中的product_id
     * @return VO
     */
    @DeleteMapping("/delete/{id}")
    public ResultVo delete(@PathVariable("id") Integer id){
        boolean removed = this.productInfoService.removeById(id);
        if(removed) return ResultVoUtil.success(null);
        return ResultVoUtil.fail();
    }


    /**修改商品状态
     *
     * @param id
     * @param status
     * @return
     */
    @PutMapping("/updateStatus/{id}/{status}")
    public ResultVo updateStatus(@PathVariable("id")Integer id , @PathVariable("status") boolean status){
        Integer statusInt = 0;
        if(status) statusInt = 1;
        boolean updated = this.productInfoService.updateStatusById(statusInt, id);
        if(updated) return ResultVoUtil.success(null);
        return ResultVoUtil.fail();
    }

    /**修改商品
     *
     * @param productForm 前端JSON数据封装成Form
     * @return VO
     */
    @PutMapping("/update")
    public ResultVo update(@RequestBody ProductForm productForm){
        ProductInfo productInfo = new ProductInfo();
        BeanUtils.copyProperties(productForm,productInfo);
        if(productForm.isProductStatus()) {
            productInfo.setProductStatus(1);
        } else {
            productInfo.setProductStatus(0);
        }
        productInfo.setCategoryType(productForm.getProductCategory().getCategoryType());
        boolean updated = this.productInfoService.updateById(productInfo);
        if(updated) return ResultVoUtil.success(null);
        return ResultVoUtil.fail();
    }

}
