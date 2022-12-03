package com.gougou.FactoryMethod;

/**
 * @author fangxu
 * on date:2022/1/4
 */
class ConcreteProductA implements Product
{
    @Override
    public void use()
    {
        System.out.println("具体产品A显示...");
    }
}
