package decorator;

/**
 * @author fangxu
 * on date:2022/1/4
 */
public class DecoratorPattern
{
    public static void main(String[] args)
    {
        Component component = new ConcreteComponent();
        component.operation();
        System.out.println("---------------------------------");
        Component decorator = new ConcreteDecorator(component);
        decorator.operation();
    }
}
//抽象构件角色
interface  Component
{
    public void operation();
}
//具体构件角色
class ConcreteComponent implements Component
{
    public ConcreteComponent()
    {
        System.out.println("创建具体构件角色");
    }
    public void operation()
    {
        System.out.println("调用具体构件角色的方法operation()");
    }
}
//抽象装饰角色
class Decorator implements Component
{
    private Component component;
    public Decorator(Component component)
    {
        this.component=component;
    }
    public void operation()
    {
        component.operation();
    }
}
//具体装饰角色
class ConcreteDecorator extends Decorator
{
    public ConcreteDecorator(Component component)
    {
        super(component);
    }
    public void operation()
    {
        super.operation();
        addBehavior();
    }
    public void addBehavior()
    {
        System.out.println("为具体构件角色增加额外的功能addBehavior()");
    }
}