package springboot.demo.controller;

/**
 * className:ProductController
 * package:com.gougou.controller
 * Description:
 *
 * @Date:2022/12/311:20
 * @Author:fangxu6@gmail.com
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.demo.entity.Product;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController

@RequestMapping("/product")

public class ProductController {


    /**
     * 获取商品列表
     *
     * @return
     */

    @GetMapping("/list")

    public Map list() {

        // 模拟查询商品逻辑

        Product product = new Product();

        product.setProductName("小米粥");

        product.setProductPrice(new BigDecimal(2.0));

        product.setProductStock(100);


        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("code", 000);

        resultMap.put("message", "成功");

        resultMap.put("data", Arrays.asList(product));

        return resultMap;

    }

}
