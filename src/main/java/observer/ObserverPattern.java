package observer;

import java.util.*;
/**
 * @author fangxu
 * on date:2022/1/4
 */
public class ObserverPattern
{
    public static void main(String[] args)
    {
        ConcreteSubject subject = new ConcreteSubject();
        Observer obsA = new ConcreteObserverA();
        Observer obsB = new ConcreteObserverB();
        subject.add(obsA);
        subject.add(obsB);
        subject.setState(0);
    }
}
//抽象目标
abstract class Subject
{
    protected List<Observer> observerList = new ArrayList<Observer>();
    //增加观察者方法
    public void add(Observer observer)
    {
        observerList.add(observer);
    }
    //删除观察者方法
    public void remove(Observer observer)
    {
        observerList.remove(observer);
    }
    public abstract void notifyObservers(); //通知观察者方法
}
//具体目标
class ConcreteSubject extends Subject
{
    private Integer state;
    public void setState(Integer state){
        this.state = state;

        // 状态改变通知观察者
        notifyObservers();
    }
    public void notifyObservers()
    {
        System.out.println("具体目标状态发生改变...");
        System.out.println("--------------");

        for(Observer obs:observerList)
        {
            obs.process();
        }

    }
}
//抽象观察者
interface Observer
{
    void process(); //具体的处理
}
//具体观察者A
class ConcreteObserverA implements Observer
{
    public void process()
    {
        System.out.println("具体观察者A处理！");
    }
}
//具体观察者B
class ConcreteObserverB implements Observer
{
    public void process()
    {
        System.out.println("具体观察者B处理！");
    }
}
