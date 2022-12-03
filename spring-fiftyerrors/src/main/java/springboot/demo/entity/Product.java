package springboot.demo.entity;

/**
 * className:Product
 * package:com.gougou.entity
 * Description:
 *
 * @Date:2022/12/310:50
 * @Author:fangxu6@gmail.com
 */

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {

    /**
     * 商品名称.
     */

    private String productName;

    /**
     * 商品价格.
     */

    private BigDecimal productPrice;

    /**
     * 商品库存。
     */
    private int productStock;

}
