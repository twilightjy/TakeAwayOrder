package cn.hust.feign;

import cn.hust.entity.ProductInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 *借助于nacos和feign，实现在order项目调用product项目中的服务
 */
@FeignClient("product")
public interface ProductFeignClient {

    @GetMapping("/buyer/product/findById/{id}")
    ProductInfo findById(@PathVariable("id") Integer id);

    @PutMapping("/buyer/product/subStock/{id}/{quantity}")
    boolean subStock(@PathVariable("id") Integer id, @PathVariable("quantity") Integer quantity);

}
