package FactoryMethod;

/**
 * @author fangxu
 * on date:2022/1/4
 */
class ConcreteFactoryA implements Factory
{
    @Override
    public Product createProduct()
    {
        System.out.println("具体工厂A生成-->具体产品A.");
        return new ConcreteProductA();
    }
}
