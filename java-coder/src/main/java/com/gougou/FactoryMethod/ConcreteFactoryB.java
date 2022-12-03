package com.gougou.FactoryMethod;

/**
 * @author fangxu
 * on date:2022/1/4
 */
class ConcreteFactoryB implements Factory
{
    @Override
    public Product createProduct()
    {
        System.out.println("具体工厂B生成-->具体产品B.");
        return new ConcreteProductB();
    }
}
