package FactoryMethod;

/**
 * @author fangxu
 * on date:2022/1/4
 */
class ConcreteProductB implements Product {
    @Override
    public void use()
    {
        System.out.println("具体产品B显示...");
    }
}
