package cn.hust;

import cn.hust.service.ProductCategoryService;
import cn.hust.vo.ProductCategoryVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProductApplicationTests {

    @Autowired
    private ProductCategoryService productCategoryService ;

    /**
     *测试ProductCategoryServiceImpl的ProductCategoryVoList()的方法
     */
    @Test
    void contextLoads() {
        System.out.println(this.productCategoryService.ProductCategoryVoList());
    }

}
