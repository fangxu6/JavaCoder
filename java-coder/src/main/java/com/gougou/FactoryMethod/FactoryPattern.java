package com.gougou.FactoryMethod;

/**
 * @author fangxu
 * on date:2022/1/4
 */
public class FactoryPattern
{
    public static void main(String[] args)
    {
        Factory factory = new ConcreteFactoryA();
        Product product = factory.createProduct();
        product.use();
    }
}
